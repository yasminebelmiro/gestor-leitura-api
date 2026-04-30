package ifgoiano.gestor_leitura_api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    @JoinTable(
        name = "livro_autor", 
        joinColumns = @JoinColumn(name = "livro_id"), 
        inverseJoinColumns = @JoinColumn(name = "autor_id") 
    )
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(
        name = "livro_generos", 
        joinColumns = @JoinColumn(name = "livro_id"), 
        inverseJoinColumns = @JoinColumn(name = "genero_id") 
    )
    private List<Genero> generos;

    @OneToMany
    @JoinColumn(name = "avaliacao_id")
    private List<Resenha> avaliacao;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    @Column(nullable = false)
    private Date dataPublicacao;

    @Column(nullable = false)
    private String capa_url;

    public Livro() {

    }

    public Livro(Long id, String isbn, int numeroPaginas, List<Autor> autores, List<Genero> generos, Editora editora,
            List<Resenha> avaliacao, Date dataPublicacao, String capa_ur) {
        this.id = id;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.autores = autores;
        this.generos = generos;
        this.editora = editora;
        this.avaliacao = avaliacao;
        this.dataPublicacao = dataPublicacao;
        this.capa_url = capa_ur;
    }
}
