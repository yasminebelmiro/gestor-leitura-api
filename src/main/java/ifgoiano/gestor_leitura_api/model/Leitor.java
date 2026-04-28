package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "leitor")
public class Leitor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany
    @JoinColumn(name = "estantes_id")
    private List<Estante> estantes;

    @OneToMany
    @JoinColumn(name = "metas_id")
    private List<MetaAnual> metas;

    public Leitor() {

    }

    public Leitor(List<Estante> estantes, Long id, String nickName, String email, List<MetaAnual> metas) {
        this.estantes = estantes;
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.metas = metas;
    }
}
