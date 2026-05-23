package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(
        name = "meta_anual",
        uniqueConstraints = @UniqueConstraint(name = "uk_meta_leitor_ano", columnNames = {"leitor_id", "ano"})
)
public class MetaAnual implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1900, message = "O ano da meta é inválido.")
    @Max(value = 2100, message = "O ano da meta é inválido.")
    @Column(nullable = false)
    private int ano;

    @Positive(message = "A quantidade alvo deve ser maior que zero.")
    @Column(name = "quantidade_alvo", nullable = false)
    private int quantidadeAlvo;

    @PositiveOrZero(message = "A quantidade alcançada não pode ser negativa.")
    @Column(name = "quantidade_alcancada", nullable = false)
    private int quantidadeAlcancada;

    @NotNull(message = "A meta anual deve pertencer a um leitor.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "leitor_id", nullable = false)
    private Leitor leitor;

    public MetaAnual() {

    }

    public MetaAnual(Long id, int ano, int quantidadeAlvo, int quantidadeAlcancada, Leitor leitor) {
        this.id = id;
        this.ano = ano;
        this.quantidadeAlvo = quantidadeAlvo;
        this.quantidadeAlcancada = quantidadeAlcancada;
        this.leitor = leitor;
    }

    public void atualizarProgresso(int livrosLidos) {
        if (livrosLidos < 0) {
            throw new IllegalArgumentException("A quantidade de livros lidos não pode ser negativa.");
        }
        this.quantidadeAlcancada = livrosLidos;
    }

    public boolean metaBatida() {
        return quantidadeAlcancada >= quantidadeAlvo;
    }

    public double percentualConclusao() {
        if (quantidadeAlvo <= 0) {
            return 0.0;
        }
        return (quantidadeAlcancada * 100.0) / quantidadeAlvo;
    }

    public static MetaAnual criarParaAnoAtual(Leitor leitor, int quantidadeAlvo) {
        return new MetaAnual(null, LocalDate.now().getYear(), quantidadeAlvo, 0, leitor);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + this.ano;
        hash = 71 * hash + this.quantidadeAlvo;
        hash = 71 * hash + this.quantidadeAlcancada;
        hash = 71 * hash + Objects.hashCode(this.leitor);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetaAnual meta)) return false;
        return id != null && Objects.equals(id, meta.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuantidadeAlvo() {
        return quantidadeAlvo;
    }

    public void setQuantidadeAlvo(int quantidadeAlvo) {
        this.quantidadeAlvo = quantidadeAlvo;
    }

    public int getQuantidadeAlcancada() {
        return quantidadeAlcancada;
    }

    public void setQuantidadeAlcancada(int quantidadeAlcancada) {
        this.quantidadeAlcancada = quantidadeAlcancada;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }
}
