package ifgoiano.gestor_leitura_api.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "leitor",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_leitor_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_leitor_nickname", columnNames = "nickname")
        }
)
public class Leitor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nickname é obrigatório.")
    @Size(min = 3, max = 80, message = "O nickname deve ter entre 3 e 80 caracteres.")
    @Column(name = "nickname", nullable = false, length = 80)
    private String nickName;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 180, message = "O e-mail deve ter no máximo 180 caracteres.")
    @Column(nullable = false, length = 180)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 255, message = "A senha deve ter no mínimo 8 caracteres.")
    @Column(nullable = false, length = 255)
    private String senha;

    @OneToMany(mappedBy = "leitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estante> estantes = new ArrayList<>();

    @OneToMany(mappedBy = "leitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MetaAnual> metas = new ArrayList<>();

    public Leitor() {

    }

    public Leitor(List<Estante> estantes, Long id, String nickName, String email, String senha, List<MetaAnual> metas) {
        this.estantes = estantes != null ? estantes : new ArrayList<>();
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.senha = senha;
        this.metas = metas != null ? metas : new ArrayList<>();
    }

    public Estante criarEstante(String nome) {
        Estante estante = new Estante();
        estante.setNome(nome);
        estante.setLeitor(this);
        this.estantes.add(estante);
        return estante;
    }

    public void adicionarMeta(MetaAnual meta) {
        if (meta == null) {
            throw new IllegalArgumentException("A meta anual não pode ser nula.");
        }
        if (buscarMetaPorAno(meta.getAno()).isPresent()) {
            throw new IllegalArgumentException("O leitor já possui meta cadastrada para o ano " + meta.getAno() + ".");
        }
        meta.setLeitor(this);
        this.metas.add(meta);
    }

    public Optional<MetaAnual> buscarMetaPorAno(int ano) {
        return metas.stream()
                .filter(meta -> meta.getAno() == ano)
                .findFirst();
    }

    public boolean verificarMetaBatida(int ano) {
        return buscarMetaPorAno(ano)
                .map(meta -> meta.getQuantidadeAlcancada() >= meta.getQuantidadeAlvo())
                .orElse(false);
    }

    public double calcularProgressoGeral() {
        List<ItemEstante> itensDoLeitor = streamItens().toList();

        int totalPaginas = itensDoLeitor.stream()
                .filter(item -> item.getLivro() != null)
                .mapToInt(item -> item.getLivro().getNumeroPaginas())
                .sum();

        if (totalPaginas == 0) {
            return 0.0;
        }

        int paginasLidas = itensDoLeitor.stream()
                .mapToInt(ItemEstante::getPaginaAtualConsiderada)
                .sum();

        return (paginasLidas * 100.0) / totalPaginas;
    }

    public long contarLivrosLidosNoAno(int ano) {
        return streamItens()
                .filter(item -> item.getStatus() == StatusLeitura.LIDO)
                .filter(item -> item.getDataConclusao() != null && item.getDataConclusao().getYear() == ano)
                .map(ItemEstante::getLivro)
                .filter(Objects::nonNull)
                .map(livro -> livro.getId() != null ? livro.getId() : System.identityHashCode(livro))
                .distinct()
                .count();
    }

    public void recalcularMetaDoAno(int ano) {
        buscarMetaPorAno(ano).ifPresent(meta -> meta.atualizarProgresso((int) contarLivrosLidosNoAno(ano)));
    }

    private Stream<ItemEstante> streamItens() {
        if (estantes == null) {
            return Stream.empty();
        }

        return estantes.stream()
                .filter(Objects::nonNull)
                .flatMap(estante -> estante.getItens() == null ? Stream.empty() : estante.getItens().stream());
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Estante> getEstantes() {
        return estantes;
    }

    public void setEstantes(List<Estante> estantes) {
        this.estantes = estantes;
    }

    public List<MetaAnual> getMetas() {
        return metas;
    }

    public void setMetas(List<MetaAnual> metas) {
        this.metas = metas != null ? metas : new ArrayList<>();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((estantes == null) ? 0 : estantes.hashCode());
        result = prime * result + ((metas == null) ? 0 : metas.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Leitor leitor)) return false;
        return id != null && Objects.equals(id, leitor.id);
    }

}
