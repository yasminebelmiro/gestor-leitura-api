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

    @OneToMany
    @JoinColumn(name = "estante_id")
    private Estante estante;

    @OneToMany
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column
    private String status;

    @OneToMany
    private List<RegistroLeitura> registrosLeitura;

    public ItemEstante() {

    }

    public ItemEstante(Long id, Estante estante, Livro livro, String status, List<RegistroLeitura> registrosLeitura) {
        this.id = id;
        this.estante = estante;
        this.livro = livro;
        this.status = status;
        this.registrosLeitura = registrosLeitura;
    }
}
