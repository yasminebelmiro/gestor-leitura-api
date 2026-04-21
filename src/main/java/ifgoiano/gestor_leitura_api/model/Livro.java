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
    private String isbn;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private int numeroPaginas;


    @OneToMany
    private List<Autor> autores;

    @OneToMany
    private List<Genero> generos;

    @OneToMany
    private Editora editora;

    @OneToMany
    private List<Resenha> resenhas;

    @Column(nullable = false)
    private Date dataPublicacao;

    public Livro (){

    }

    public Livro(Long id, String isbn, String titulo, int numeroPaginas, List<Autor> autores, List<Genero> generos, Editora editora, List<Resenha> resenhas, Date dataPublicacao) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.numeroPaginas = numeroPaginas;
        this.autores = autores;
        this.generos = generos;
        this.editora = editora;
        this.resenhas = resenhas;
        this.dataPublicacao = dataPublicacao;
    }
}
