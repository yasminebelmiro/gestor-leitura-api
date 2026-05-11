package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "registro_leitura")
public class RegistroLeitura implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private int paginaAtual;

    @Column(length = 1000)
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEstante item;

    public RegistroLeitura() {

    }

    public RegistroLeitura(Long id, ItemEstante item, Date data, int paginaAtual, String comentarios) {
        this.id = id;
        this.item = item;
        this.data = data;
        this.paginaAtual = paginaAtual;
        this.comentarios = comentarios;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.data);
        hash = 41 * hash + this.paginaAtual;
        hash = 41 * hash + Objects.hashCode(this.comentarios);
        hash = 41 * hash + Objects.hashCode(this.item);
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
        final RegistroLeitura other = (RegistroLeitura) obj;
        if (this.paginaAtual != other.paginaAtual) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return Objects.equals(this.item, other.item);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public ItemEstante getItem() {
        return item;
    }

    public void setItem(ItemEstante item) {
        this.item = item;
    }
}
