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
@Table(name = "meta_anual")
public class MetaAnual implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private int quantidadeAlvo;

    @Column(nullable = false)
    private int quantidadeAlcancada;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    public MetaAnual() {

    }

    public MetaAnual(Long id, int ano, int quantidadeAlvo, int quantidadeAlcancada, Leitor leitor) {
        this.id = id;
        this.ano = ano;
        this.quantidadeAlvo = quantidadeAlvo;
        this.quantidadeAlcancada = quantidadeAlcancada;
        this.leitor = leitor;
    }
}
