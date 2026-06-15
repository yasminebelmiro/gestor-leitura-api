package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "estante",
        uniqueConstraints = @UniqueConstraint(name = "uk_estante_leitor_nome", columnNames = {"leitor_id", "nome"})
)
public class Estante implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "O nome da estante é obrigatório.")
    @Size(max = 120, message = "O nome da estante deve ter no máximo 120 caracteres.")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotNull(message = "A estante deve pertencer a um leitor.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "leitor_id", nullable = false)
    private Leitor leitor;

    @OneToMany(mappedBy = "estante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEstante> itens = new ArrayList<>();

    public Estante() {
    }

    public Estante(String nome, long id, Leitor leitor) {
        this.nome = nome;
        this.id = id;
        this.leitor = leitor;
    }

    public ItemEstante adicionarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("O livro não pode ser nulo.");
        }

        boolean livroJaExisteNaEstante = itens.stream()
                .anyMatch(item -> item.getLivro() != null && item.getLivro().equals(livro));

        if (livroJaExisteNaEstante) {
            throw new IllegalArgumentException("Este livro já está nesta estante.");
        }

        ItemEstante item = new ItemEstante();
        item.setEstante(this);
        item.setLivro(livro);
        item.setStatus(StatusLeitura.QUERO_LER);
        itens.add(item);
        return item;
    }

    public int calcularTotalPaginas() {
        if (itens == null || itens.isEmpty()) {
            return 0;
        }

        return itens.stream()
                .filter(item -> item.getLivro() != null)
                .mapToInt(item -> item.getLivro().getNumeroPaginas())
                .sum();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public List<ItemEstante> getItens() {
        return itens;
    }

    public void setItens(List<ItemEstante> itens) {
        this.itens = itens != null ? itens : new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.leitor);
        hash = 97 * hash + Objects.hashCode(this.itens);
        return hash;
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
        final Estante other = (Estante) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.leitor, other.leitor)) {
            return false;
        }
        return Objects.equals(this.itens, other.itens);
    }

    
}
