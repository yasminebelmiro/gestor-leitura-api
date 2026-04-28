package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode

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
}
