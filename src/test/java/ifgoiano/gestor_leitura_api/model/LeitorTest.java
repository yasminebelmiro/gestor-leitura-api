package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeitorTest {

    @Test
    void deveCriarEstanteVinculadaAoLeitor() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");

        Estante estante = leitor.criarEstante("Favoritos");

        assertEquals("Favoritos", estante.getNome());
        assertSame(leitor, estante.getLeitor());
        assertEquals(1, leitor.getEstantes().size());
        assertSame(estante, leitor.getEstantes().get(0));
    }

    @Test
    void deveAdicionarMetaEVincularLeitor() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        MetaAnual meta = new MetaAnual(null, 2026, 12, 0, null);

        leitor.adicionarMeta(meta);

        assertSame(leitor, meta.getLeitor());
        assertEquals(1, leitor.getMetas().size());
        assertSame(meta, leitor.buscarMetaPorAno(2026).orElseThrow());
    }

    @Test
    void naoDeveAdicionarMetaNula() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> leitor.adicionarMeta(null)
        );

        assertEquals("A meta anual não pode ser nula.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarDuasMetasParaMesmoAno() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        leitor.adicionarMeta(new MetaAnual(null, 2026, 12, 0, null));

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> leitor.adicionarMeta(new MetaAnual(null, 2026, 20, 0, null))
        );

        assertEquals("O leitor já possui meta cadastrada para o ano 2026.", erro.getMessage());
        assertEquals(1, leitor.getMetas().size());
    }

    @Test
    void deveVerificarMetaBatida() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        leitor.adicionarMeta(new MetaAnual(null, 2026, 10, 10, null));

        assertTrue(leitor.verificarMetaBatida(2026));
        assertFalse(leitor.verificarMetaBatida(2025));
    }

    @Test
    void deveCalcularProgressoGeralComBaseNasPaginas() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Estante estante = TestFixtures.estante(leitor, "Principal");
        ItemEstante primeiro = estante.adicionarLivro(TestFixtures.livro(1L, "A", 100));
        ItemEstante segundo = estante.adicionarLivro(TestFixtures.livro(2L, "B", 300));
        primeiro.adicionarRegistroLeitura(LocalDate.of(2026, 1, 1), 100, null);
        segundo.adicionarRegistroLeitura(LocalDate.of(2026, 1, 1), 100, null);

        assertEquals(50.0, leitor.calcularProgressoGeral(), 0.0001);
    }

    @Test
    void deveRetornarZeroQuandoNaoHouverPaginasCadastradas() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");

        assertEquals(0.0, leitor.calcularProgressoGeral(), 0.0001);
    }

    @Test
    void deveContarSomenteLivrosLidosNoAnoInformado() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Estante estante = TestFixtures.estante(leitor, "Principal");
        ItemEstante lidoEm2026 = estante.adicionarLivro(TestFixtures.livro(1L, "A", 100));
        ItemEstante lidoEm2025 = estante.adicionarLivro(TestFixtures.livro(2L, "B", 100));
        ItemEstante aindaLendo = estante.adicionarLivro(TestFixtures.livro(3L, "C", 100));
        lidoEm2026.marcarComoLido(LocalDate.of(2026, 1, 10));
        lidoEm2025.marcarComoLido(LocalDate.of(2025, 1, 10));
        aindaLendo.marcarComoLendo();

        assertEquals(1L, leitor.contarLivrosLidosNoAno(2026));
    }

    @Test
    void naoDeveContarMesmoLivroDuasVezes() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Estante primeira = TestFixtures.estante(leitor, "Primeira");
        Estante segunda = TestFixtures.estante(leitor, "Segunda");
        Livro livro = TestFixtures.livro(1L, "Livro", 100);
        ItemEstante itemUm = primeira.adicionarLivro(livro);
        ItemEstante itemDois = segunda.adicionarLivro(livro);
        itemUm.marcarComoLido(LocalDate.of(2026, 1, 1));
        itemDois.marcarComoLido(LocalDate.of(2026, 2, 1));

        assertEquals(1L, leitor.contarLivrosLidosNoAno(2026));
    }

    @Test
    void deveRecalcularQuantidadeAlcancadaDaMeta() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        MetaAnual meta = new MetaAnual(null, 2026, 5, 0, null);
        leitor.adicionarMeta(meta);
        Estante estante = TestFixtures.estante(leitor, "Principal");
        estante.adicionarLivro(TestFixtures.livro(1L, "A", 100))
                .marcarComoLido(LocalDate.of(2026, 1, 1));
        estante.adicionarLivro(TestFixtures.livro(2L, "B", 100))
                .marcarComoLido(LocalDate.of(2026, 2, 1));

        leitor.recalcularMetaDoAno(2026);

        assertEquals(2, meta.getQuantidadeAlcancada());
    }
}
