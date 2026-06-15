package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "resenha",
        uniqueConstraints = @UniqueConstraint(name = "uk_resenha_leitor_livro", columnNames = {"leitor_id", "livro_id"})
)
public class Resenha implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O texto da resenha é obrigatório.")
    @Size(max = 2000, message = "A resenha deve ter no máximo 2000 caracteres.")
    @Column(nullable = false, length = 2000)
    private String texto;

    @NotNull(message = "A avaliação é obrigatória.")
    @DecimalMin(value = "0.0", message = "A avaliação mínima é 0.")
    @DecimalMax(value = "5.0", message = "A avaliação máxima é 5.")
    @Column(nullable = false)
    private Double avaliacao;

    @NotNull(message = "A resenha deve possuir um leitor.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "leitor_id", nullable = false)
    private Leitor leitor;

    @NotNull(message = "A resenha deve possuir um livro.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    public Resenha(Double avaliacao, Long id, Leitor leitor, Livro livro, String texto) {
        this.avaliacao = avaliacao;
        this.id = id;
        this.leitor = leitor;
        this.livro = livro;
        this.texto = texto;
    }

    public Resenha() {

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.texto);
        hash = 61 * hash + Objects.hashCode(this.avaliacao);
        hash = 61 * hash + Objects.hashCode(this.leitor);
        hash = 61 * hash + Objects.hashCode(this.livro);
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
        final Resenha other = (Resenha) obj;
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.avaliacao, other.avaliacao)) {
            return false;
        }
        if (!Objects.equals(this.leitor, other.leitor)) {
            return false;
        }
        return Objects.equals(this.livro, other.livro);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

}
