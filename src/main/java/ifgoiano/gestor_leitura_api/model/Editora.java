package ifgoiano.gestor_leitura_api.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "editora",
        uniqueConstraints = @UniqueConstraint(name = "uk_editora_nome", columnNames = "nome")
)
public class Editora implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "O nome da editora é obrigatório.")
    @Size(max = 150, message = "O nome da editora deve ter no máximo 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String nome;

    @OneToMany(mappedBy = "editora")
    private List<Livro> obras = new ArrayList<>();

    public Editora(Long id, String nome, List<Livro> obras) {
        this.id = id;
        this.nome = nome;
        this.obras = obras != null ? obras : new ArrayList<>();
    }

    public Editora() {
    }

    public String catalogo() {
        if (obras == null || obras.isEmpty()) {
            return "Nenhuma obra cadastrada para esta editora.";
        }

        return obras.stream()
                .map(Livro::getTitulo)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }

    // public String lancamentosDoAno(int ano) {
    //     if (obras == null || obras.isEmpty()) {
    //         return "Nenhum lançamento encontrado.";
    //     }

    //     String resultado = obras.stream()
    //             .filter(livro -> livro.getDataPublicacao() != null)
    //             .filter(livro -> livro.getDataPublicacao().getYear() == ano)
    //             .map(Livro::getTitulo)
    //             .collect(Collectors.joining(", "));

    //     return resultado.isBlank() ? "Nenhum lançamento encontrado." : resultado;
    // }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getObras() {
        return obras;
    }

    public void setObras(List<Livro> obras) {
        this.obras = obras != null ? obras : new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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
        final Editora other = (Editora) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.obras, other.obras);
    }

    

}
