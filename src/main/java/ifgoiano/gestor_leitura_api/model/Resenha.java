package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "resenha")
public class Resenha implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private float avaliacao;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Resenha(){

    }

    public Resenha(Long id, String texto, float avaliacao, Leitor leitor, Livro livro) {
        this.id = id;
        this.texto = texto;
        this.avaliacao = avaliacao;
        this.leitor = leitor;
        this.livro = livro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((texto == null) ? 0 : texto.hashCode());
        result = prime * result + Float.floatToIntBits(avaliacao);
        result = prime * result + ((leitor == null) ? 0 : leitor.hashCode());
        result = prime * result + ((livro == null) ? 0 : livro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Resenha other = (Resenha) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (texto == null) {
            if (other.texto != null)
                return false;
        } else if (!texto.equals(other.texto))
            return false;
        if (Float.floatToIntBits(avaliacao) != Float.floatToIntBits(other.avaliacao))
            return false;
        if (leitor == null) {
            if (other.leitor != null)
                return false;
        } else if (!leitor.equals(other.leitor))
            return false;
        if (livro == null) {
            if (other.livro != null)
                return false;
        } else if (!livro.equals(other.livro))
            return false;
        return true;
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

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
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
