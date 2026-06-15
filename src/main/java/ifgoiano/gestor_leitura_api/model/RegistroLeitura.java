package ifgoiano.gestor_leitura_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "registro_leitura"
)
public class RegistroLeitura implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do registro é obrigatória.")
    @Column(nullable = false)
    private LocalDate data;

    @Min(value = 0, message = "A página atual não pode ser menor que zero.")
    @Column(name = "pagina_atual", nullable = false)
    private int paginaAtual;

    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres.")
    @Column(length = 1000)
    private String comentarios;

    @NotNull(message = "O registro deve pertencer a um item de estante.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEstante item;

    public RegistroLeitura(Long id, ItemEstante item, LocalDate data, int paginaAtual, String comentarios) {
        this.id = id;
        this.item = item;
        this.data = data;
        this.paginaAtual = paginaAtual;
        this.comentarios = comentarios;
    }

    public RegistroLeitura() {

    }
public void atualizarProgresso(int pagina, String comentario) {
        if (item == null || item.getLivro() == null) {
            throw new IllegalArgumentException("O registro deve estar vinculado a um item com livro.");
        }
        if (pagina < 0) {
            throw new IllegalArgumentException("A página atual não pode ser menor que zero.");
        }
        if (pagina > item.getLivro().getNumeroPaginas()) {
            throw new IllegalArgumentException("A página atual não pode ultrapassar o total de páginas do livro.");
        }

        this.paginaAtual = pagina;
        this.comentarios = comentario;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroLeitura registro)) return false;
        return id != null && Objects.equals(id, registro.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
