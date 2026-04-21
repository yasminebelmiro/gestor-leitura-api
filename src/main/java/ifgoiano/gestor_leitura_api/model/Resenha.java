package ifgoiano.gestor_leitura_api.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode

@Entity
@Table(name = "resenha")
public class Resenha implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private float avaliacao;

    //Não sei oq vai aki
    private Leitor leitor;

    //Não sei oq vai aki
    private Livro livro;

    public Resenha(){

    }

    public Resenha(Long id, String texto, float avaliacao, Leitor leitor, Livro livro) {
        this.id = id;
        this.texto = texto;
        this.avaliacao = avaliacao;
        this.leitor = leitor;
        this.livro = livro;
    }
}
