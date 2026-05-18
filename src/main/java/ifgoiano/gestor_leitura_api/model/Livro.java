package ifgoiano.gestor_leitura_api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
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
    private String titulo;

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

    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Livro(Long id, String googleVolumeId, String titulo, String isbn, int numeroPaginas, List<Autor> autores,
            List<Genero> generos, List<Resenha> avaliacao, Editora editora, Date dataPublicacao, String capa_url) {
        this.id = id;
        this.googleVolumeId = googleVolumeId;
        this.titulo = titulo;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.autores = autores;
        this.generos = generos;
        this.avaliacao = avaliacao;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.capa_url = capa_url;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.googleVolumeId);
        hash = 41 * hash + Objects.hashCode(this.titulo);
        hash = 41 * hash + Objects.hashCode(this.isbn);
        hash = 41 * hash + this.numeroPaginas;
        hash = 41 * hash + Objects.hashCode(this.autores);
        hash = 41 * hash + Objects.hashCode(this.generos);
        hash = 41 * hash + Objects.hashCode(this.avaliacao); 
        hash = 41 * hash + Objects.hashCode(this.editora);
        hash = 41 * hash + Objects.hashCode(this.dataPublicacao);
        hash = 41 * hash + Objects.hashCode(this.capa_url);
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
        final Livro other = (Livro) obj;
        if (this.numeroPaginas != other.numeroPaginas) {
            return false;
        }
        if (!Objects.equals(this.googleVolumeId, other.googleVolumeId)) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.isbn, other.isbn)) {
            return false;
        }
        if (!Objects.equals(this.capa_url, other.capa_url)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.autores, other.autores)) {
            return false;
        }
        if (!Objects.equals(this.generos, other.generos)) {
            return false;
        }
        if (!Objects.equals(this.avaliacao, other.avaliacao)) {
            return false;
        }
        if (!Objects.equals(this.editora, other.editora)) {
            return false;
        }
        return Objects.equals(this.dataPublicacao, other.dataPublicacao);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleVolumeId() {
        return googleVolumeId;
    }

    public void setGoogleVolumeId(String googleVolumeId) {
        this.googleVolumeId = googleVolumeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Resenha> getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(List<Resenha> avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getCapa_url() {
        return capa_url;
    }

    public void setCapa_url(String capa_url) {
        this.capa_url = capa_url;
    }

 
}
