package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode

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
}
