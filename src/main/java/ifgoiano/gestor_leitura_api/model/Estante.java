package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "estante")
public class Estante implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    //Não sei oq vai aki
    private Leitor leitor;

    public Estante() {
    }

    public Estante(String nome, long id, Leitor leitor) {
        this.nome = nome;
        this.id = id;
        this.leitor = leitor;
    }
}
