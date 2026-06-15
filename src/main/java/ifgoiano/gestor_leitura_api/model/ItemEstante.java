package ifgoiano.gestor_leitura_api.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "item_estante",
        uniqueConstraints = @UniqueConstraint(name = "uk_item_estante_livro", columnNames = {"estante_id", "livro_id"})
)
public class ItemEstante implements Serializable {
    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O status de leitura é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusLeitura status = StatusLeitura.QUERO_LER;

    @NotNull(message = "O item deve pertencer a uma estante.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "estante_id", nullable = false)
    private Estante estante;

    @NotNull(message = "O item deve estar vinculado a um livro.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroLeitura> registros = new ArrayList<>();

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    public ItemEstante() {

    }

    public ItemEstante(Long id, Estante estante, Livro livro, StatusLeitura status, List<RegistroLeitura> registros) {
        this.id = id;
        this.estante = estante;
        this.livro = livro;
        this.status = status != null ? status : StatusLeitura.QUERO_LER;
        this.registros = registros != null ? registros : new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.status);
        hash = 17 * hash + Objects.hashCode(this.estante);
        hash = 17 * hash + Objects.hashCode(this.livro);
        hash = 17 * hash + Objects.hashCode(this.registros);
        return hash;
    }

    public void moverParaOutraEstante(Estante novaEstante) {
        if (novaEstante == null) {
            throw new IllegalArgumentException("A nova estante não pode ser nula.");
        }
        
        if (this.estante != null && this.estante.getLeitor() != null && novaEstante.getLeitor() != null
                && !this.estante.getLeitor().equals(novaEstante.getLeitor())) {
            throw new IllegalArgumentException("Não é permitido mover o item para a estante de outro leitor.");
        }

        if (this.estante != null && this.estante.getItens() != null) {
            this.estante.getItens().remove(this);
        }
        this.estante = novaEstante;

        if (novaEstante.getItens() != null) {
            novaEstante.getItens().add(this);
        }
    }

    public void marcarComoAbandonado() {
        atualizarStatus(StatusLeitura.ABANDONADO, LocalDate.now());
    }

    public void marcarComoLido() {
        atualizarStatus(StatusLeitura.LIDO, LocalDate.now());
    }

    public void marcarComoQueroLer() {
        atualizarStatus(StatusLeitura.QUERO_LER, LocalDate.now());
    }

    public void marcarComoLendo() {
        atualizarStatus(StatusLeitura.LENDO, LocalDate.now());
    }

    public void atualizarStatus(StatusLeitura novoStatus) {
        atualizarStatus(novoStatus, LocalDate.now());
    }

    public void atualizarStatus(StatusLeitura novoStatus, LocalDate dataReferencia) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("O novo status não pode ser nulo.");
        }

        StatusLeitura statusAnterior = this.status;
        LocalDate conclusaoAnterior = this.dataConclusao;

        this.status = novoStatus;

        if (novoStatus == StatusLeitura.LIDO) {
            this.dataConclusao = dataReferencia != null ? dataReferencia : LocalDate.now();
        } else {
            this.dataConclusao = null;
        }

        sincronizarMetas(statusAnterior, conclusaoAnterior);
    }

 

    public int calcularDiasDeLeitura() {
        if (registros == null || registros.isEmpty()) {
            return 0;
        }

        LocalDate primeiraData = registros.stream()
                .map(RegistroLeitura::getData)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);

        LocalDate ultimaData = registros.stream()
                .map(RegistroLeitura::getData)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);

        if (primeiraData == null || ultimaData == null) {
            return 0;
        }

        return (int) ChronoUnit.DAYS.between(primeiraData, ultimaData) + 1;
    }

    public int getPaginaAtualConsiderada() {
        if (livro == null) {
            return 0;
        }
        if (status == StatusLeitura.LIDO) {
            return livro.getNumeroPaginas();
        }
        if (registros == null || registros.isEmpty()) {
            return 0;
        }

        return registros.stream()
                .mapToInt(RegistroLeitura::getPaginaAtual)
                .max()
                .orElse(0);
    }

    private void validarNovoRegistro(RegistroLeitura registro) {
        if (registro.getData() == null) {
            throw new IllegalArgumentException("A data do registro é obrigatória.");
        }
        if (livro == null) {
            throw new IllegalArgumentException("O item deve possuir um livro antes de registrar progresso.");
        }
        if (registro.getPaginaAtual() < 0) {
            throw new IllegalArgumentException("A página atual não pode ser menor que zero.");
        }
        if (registro.getPaginaAtual() > livro.getNumeroPaginas()) {
            throw new IllegalArgumentException("A página atual não pode ultrapassar o total de páginas do livro.");
        }
        if (registro.getPaginaAtual() < getPaginaAtualConsiderada() && status != StatusLeitura.LIDO) {
            throw new IllegalArgumentException("O progresso de leitura não pode retroceder. Use uma rotina de correção para ajustar registros antigos.");
        }
    }

    private void sincronizarMetas(StatusLeitura statusAnterior, LocalDate conclusaoAnterior) {
        if (estante == null || estante.getLeitor() == null) {
            return;
        }

        Leitor leitor = estante.getLeitor();

        if (statusAnterior == StatusLeitura.LIDO && conclusaoAnterior != null) {
            leitor.recalcularMetaDoAno(conclusaoAnterior.getYear());
        }
        if (status == StatusLeitura.LIDO && dataConclusao != null) {
            leitor.recalcularMetaDoAno(dataConclusao.getYear());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemEstante other = (ItemEstante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.estante, other.estante)) {
            return false;
        }
        if (!Objects.equals(this.livro, other.livro)) {
            return false;
        }
        return Objects.equals(this.registros, other.registros);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusLeitura getStatus() {
        return status;
    }

    public void setStatus(StatusLeitura status) {
        atualizarStatus(status, LocalDate.now());
    }

    public Estante getEstante() {
        return estante;
    }

    public void setEstante(Estante estante) {
        this.estante = estante;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<RegistroLeitura> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroLeitura> registros) {
        this.registros = registros != null ? registros : new ArrayList<>();
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

}
