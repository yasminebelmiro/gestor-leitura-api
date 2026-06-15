package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetaAnualTest {

    @Test
    void deveAtualizarProgresso() {
        MetaAnual meta = new MetaAnual(null, 2026, 12, 0, null);

        meta.atualizarProgresso(5);

        assertEquals(5, meta.getQuantidadeAlcancada());
    }

    @Test
    void naoDeveAceitarQuantidadeNegativaDeLivrosLidos() {
        MetaAnual meta = new MetaAnual(null, 2026, 12, 0, null);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> meta.atualizarProgresso(-1)
        );

        assertEquals("A quantidade de livros lidos não pode ser negativa.", erro.getMessage());
        assertEquals(0, meta.getQuantidadeAlcancada());
    }

    @Test
    void deveConsiderarMetaBatidaQuandoAlcancarOuSuperarAlvo() {
        MetaAnual igualAoAlvo = new MetaAnual(null, 2026, 10, 10, null);
        MetaAnual acimaDoAlvo = new MetaAnual(null, 2026, 10, 12, null);
        MetaAnual abaixoDoAlvo = new MetaAnual(null, 2026, 10, 9, null);

        assertTrue(igualAoAlvo.metaBatida());
        assertTrue(acimaDoAlvo.metaBatida());
        assertFalse(abaixoDoAlvo.metaBatida());
    }

    @Test
    void deveCalcularPercentualDeConclusao() {
        MetaAnual meta = new MetaAnual(null, 2026, 20, 5, null);

        assertEquals(25.0, meta.percentualConclusao(), 0.0001);
    }

    @Test
    void devePermitirPercentualAcimaDeCemQuandoMetaForSuperada() {
        MetaAnual meta = new MetaAnual(null, 2026, 10, 15, null);

        assertEquals(150.0, meta.percentualConclusao(), 0.0001);
    }

    @Test
    void deveRetornarZeroQuandoAlvoForInvalido() {
        MetaAnual meta = new MetaAnual(null, 2026, 0, 5, null);

        assertEquals(0.0, meta.percentualConclusao(), 0.0001);
    }

    @Test
    void deveCriarMetaParaAnoAtual() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");

        MetaAnual meta = MetaAnual.criarParaAnoAtual(leitor, 15);

        assertEquals(LocalDate.now().getYear(), meta.getAno());
        assertEquals(15, meta.getQuantidadeAlvo());
        assertEquals(0, meta.getQuantidadeAlcancada());
        assertSame(leitor, meta.getLeitor());
    }
}
