package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "meta_anual")
public class MetaAnual implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int quantidadeAlvo;

    @Column(nullable = false)
    private int quantidadeAlcancada;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
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
        final MetaAnual other = (MetaAnual) obj;
        if (this.ano != other.ano) {
            return false;
        }
        if (this.quantidadeAlvo != other.quantidadeAlvo) {
            return false;
        }
        if (this.quantidadeAlcancada != other.quantidadeAlcancada) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.leitor, other.leitor);
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
