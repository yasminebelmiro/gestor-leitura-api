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
    private String nome;

    @Column(nullable = false)
    private String email;

    //Não sei oq vai aki
    private List<Estante> estantes;
    //Não sei oq vai aki
    private List<MetaAnual> metasAnuais;
    //Não sei oq vai aki
    private List<Resenha> resenhas;

    public Leitor() {

    }

    public Leitor(List<Estante> estantes, List<Resenha> resenhas, Long id, String nome, String email, List<MetaAnual> metasAnuais) {
        this.estantes = estantes;
        this.resenhas = resenhas;
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.metasAnuais = metasAnuais;
    }
}
