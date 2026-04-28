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
@Table(name = "editora")
public class Editora implements Serializable {

    private static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany
    @JoinColumn(name = "obras_id")
    private List<Livro> obras;

    public Editora() {

    }

    public Editora(Long id, String nome, List<Livro> obras) {
        this.id = id;
        this.nome = nome;
        this.obras = obras;
    }

}
