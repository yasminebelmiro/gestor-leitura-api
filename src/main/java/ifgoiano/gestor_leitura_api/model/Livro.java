package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "livro")
public class Livro implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String googleVolumeId;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private int numeroPaginas;


    @ManyToMany
    @JoinColumn(name = "autores_id")
    private List<Autor> autores;

    @ManyToMany
    @JoinColumn(name = "generos_id")
    private List<Genero> generos;

    @OneToMany
    @JoinColumn(name = "avaliacao_id")
    private List<Resenha> avaliacao;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @Column(nullable = false)
    private Date dataPublicacao;

    public Livro() {

    }

    public Livro(Long id, String isbn, int numeroPaginas, List<Autor> autores, List<Genero> generos, Editora editora, List<Resenha> avaliacao, Date dataPublicacao) {
        this.id = id;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.autores = autores;
        this.generos = generos;
        this.editora = editora;
        this.avaliacao = avaliacao;
        this.dataPublicacao = dataPublicacao;
    }
}
