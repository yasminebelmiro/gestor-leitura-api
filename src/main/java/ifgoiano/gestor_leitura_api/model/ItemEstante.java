package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "item_estante")
public class ItemEstante implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Enum<StatusLeitura> status;

    @ManyToOne
    @JoinColumn(name = "estante_id")
    private Estante estante;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @OneToMany
    @JoinColumn(name = "registros_id")
    private List<RegistroLeitura> registros;

    public ItemEstante() {

    }

    public ItemEstante(Long id, Estante estante, Livro livro, Enum<StatusLeitura> status, List<RegistroLeitura> registros) {
        this.id = id;
        this.estante = estante;
        this.livro = livro;
        this.status = status;
        this.registros = registros;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.status);
        hash = 17 * hash + Objects.hashCode(this.estante);
        hash = 17 * hash + Objects.hashCode(this.livro);
        hash = 17 * hash + Objects.hashCode(this.registros);
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
        final ItemEstante other = (ItemEstante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.estante, other.estante)) {
            return false;
        }
        if (!Objects.equals(this.livro, other.livro)) {
            return false;
        }
        return Objects.equals(this.registros, other.registros);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enum<StatusLeitura> getStatus() {
        return status;
    }

    public void setStatus(Enum<StatusLeitura> status) {
        this.status = status;
    }

    public Estante getEstante() {
        return estante;
    }

    public void setEstante(Estante estante) {
        this.estante = estante;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<RegistroLeitura> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroLeitura> registros) {
        this.registros = registros;
    }
}
