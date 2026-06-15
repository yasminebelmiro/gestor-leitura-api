package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemEstanteTest {

    @Test
    void deveUsarQueroLerComoStatusPadrao() {
        ItemEstante item = new ItemEstante(null, null, null, null, null);

        assertEquals(StatusLeitura.QUERO_LER, item.getStatus());
        assertTrue(item.getRegistros().isEmpty());
    }

    @Test
    void deveMoverItemEntreEstantesDoMesmoLeitor() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Estante origem = TestFixtures.estante(leitor, "Origem");
        Estante destino = TestFixtures.estante(leitor, "Destino");
        ItemEstante item = origem.adicionarLivro(TestFixtures.livro(1L, "Livro", 200));

        item.moverParaOutraEstante(destino);

        assertSame(destino, item.getEstante());
        assertFalse(origem.getItens().contains(item));
        assertTrue(destino.getItens().contains(item));
    }

    @Test
    void naoDeveMoverItemParaEstanteNula() {
        ItemEstante item = new ItemEstante();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.moverParaOutraEstante(null)
        );

        assertEquals("A nova estante não pode ser nula.", erro.getMessage());
    }

    @Test
    void naoDeveMoverItemParaEstanteDeOutroLeitor() {
        Leitor primeiroLeitor = TestFixtures.leitor(1L, "primeiro");
        Leitor segundoLeitor = TestFixtures.leitor(2L, "segundo");
        Estante origem = TestFixtures.estante(primeiroLeitor, "Origem");
        Estante destino = TestFixtures.estante(segundoLeitor, "Destino");
        ItemEstante item = origem.adicionarLivro(TestFixtures.livro(1L, "Livro", 200));

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.moverParaOutraEstante(destino)
        );

        assertEquals("Não é permitido mover o item para a estante de outro leitor.", erro.getMessage());
        assertSame(origem, item.getEstante());
        assertTrue(origem.getItens().contains(item));
        assertFalse(destino.getItens().contains(item));
    }

    @Test
    void deveMarcarComoLidoERegistrarDataDeConclusao() {
        ItemEstante item = itemComLivro(200);
        LocalDate conclusao = LocalDate.of(2026, 5, 20);

        item.marcarComoLido(conclusao);

        assertEquals(StatusLeitura.LIDO, item.getStatus());
        assertEquals(conclusao, item.getDataConclusao());
    }

    @Test
    void deveLimparDataDeConclusaoAoSairDoStatusLido() {
        ItemEstante item = itemComLivro(200);
        item.marcarComoLido(LocalDate.of(2026, 5, 20));

        item.marcarComoLendo();

        assertEquals(StatusLeitura.LENDO, item.getStatus());
        assertNull(item.getDataConclusao());
    }

    @Test
    void deveUsarDataAtualQuandoConclusaoForNula() {
        ItemEstante item = itemComLivro(200);
        LocalDate hoje = LocalDate.now();

        item.marcarComoLido(null);

        assertEquals(StatusLeitura.LIDO, item.getStatus());
        assertEquals(hoje, item.getDataConclusao());
    }

    @Test
    void naoDeveAceitarStatusNulo() {
        ItemEstante item = itemComLivro(200);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.atualizarStatus(null)
        );

        assertEquals("O novo status não pode ser nulo.", erro.getMessage());
    }

    @Test
    void primeiroRegistroDeveAlterarStatusParaLendo() {
        ItemEstante item = itemComLivro(200);
        LocalDate data = LocalDate.of(2026, 1, 10);

        RegistroLeitura registro = item.adicionarRegistroLeitura(data, 25, "Início");

        assertEquals(StatusLeitura.LENDO, item.getStatus());
        assertEquals(1, item.getRegistros().size());
        assertSame(item, registro.getItem());
        assertEquals(25, registro.getPaginaAtual());
        assertEquals(data, registro.getData());
    }

    @Test
    void registroNaUltimaPaginaDeveMarcarLivroComoLido() {
        ItemEstante item = itemComLivro(200);
        LocalDate data = LocalDate.of(2026, 1, 15);

        item.adicionarRegistroLeitura(data, 200, "Finalizado");

        assertEquals(StatusLeitura.LIDO, item.getStatus());
        assertEquals(data, item.getDataConclusao());
        assertEquals(200, item.getPaginaAtualConsiderada());
    }

    @Test
    void naoDeveAdicionarRegistroSemData() {
        ItemEstante item = itemComLivro(200);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.adicionarRegistroLeitura(null, 10, null)
        );

        assertEquals("A data do registro é obrigatória.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarRegistroSemLivro() {
        ItemEstante item = new ItemEstante();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.adicionarRegistroLeitura(LocalDate.now(), 10, null)
        );

        assertEquals("O item deve possuir um livro antes de registrar progresso.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarRegistroComPaginaNegativa() {
        ItemEstante item = itemComLivro(200);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.adicionarRegistroLeitura(LocalDate.now(), -1, null)
        );

        assertEquals("A página atual não pode ser menor que zero.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarRegistroAcimaDoTotalDePaginas() {
        ItemEstante item = itemComLivro(200);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.adicionarRegistroLeitura(LocalDate.now(), 201, null)
        );

        assertEquals("A página atual não pode ultrapassar o total de páginas do livro.", erro.getMessage());
    }

    @Test
    void naoDevePermitirRetrocessoDoProgresso() {
        ItemEstante item = itemComLivro(200);
        item.adicionarRegistroLeitura(LocalDate.of(2026, 1, 10), 100, null);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> item.adicionarRegistroLeitura(LocalDate.of(2026, 1, 11), 90, null)
        );

        assertTrue(erro.getMessage().startsWith("O progresso de leitura não pode retroceder"));
        assertEquals(1, item.getRegistros().size());
    }

    @Test
    void deveCalcularDiasDeLeituraDeFormaInclusiva() {
        ItemEstante item = itemComLivro(300);
        item.adicionarRegistroLeitura(LocalDate.of(2026, 1, 10), 10, null);
        item.adicionarRegistroLeitura(LocalDate.of(2026, 1, 14), 20, null);

        assertEquals(5, item.calcularDiasDeLeitura());
    }

    @Test
    void deveRetornarZeroDiasQuandoNaoExistiremRegistrosValidos() {
        ItemEstante item = itemComLivro(200);
        RegistroLeitura registroSemData = new RegistroLeitura(null, item, null, 10, null);
        item.setRegistros(List.of(registroSemData));

        assertEquals(0, item.calcularDiasDeLeitura());
    }

    @Test
    void deveConsiderarMaiorPaginaRegistrada() {
        ItemEstante item = itemComLivro(300);
        item.setStatus(StatusLeitura.LENDO);
        item.setRegistros(List.of(
                new RegistroLeitura(null, item, LocalDate.of(2026, 1, 1), 40, null),
                new RegistroLeitura(null, item, LocalDate.of(2026, 1, 2), 120, null),
                new RegistroLeitura(null, item, LocalDate.of(2026, 1, 3), 80, null)
        ));

        assertEquals(120, item.getPaginaAtualConsiderada());
    }

    @Test
    void itemLidoDeveConsiderarTodasAsPaginas() {
        ItemEstante item = itemComLivro(300);
        item.setRegistros(List.of(
                new RegistroLeitura(null, item, LocalDate.of(2026, 1, 1), 100, null)
        ));
        item.marcarComoLido(LocalDate.of(2026, 1, 2));

        assertEquals(300, item.getPaginaAtualConsiderada());
    }

    @Test
    void deveSincronizarMetaAoConcluirEReabrirLivro() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        MetaAnual meta = new MetaAnual(null, 2026, 3, 0, leitor);
        leitor.adicionarMeta(meta);
        Estante estante = TestFixtures.estante(leitor, "Principal");
        ItemEstante item = estante.adicionarLivro(TestFixtures.livro(1L, "Livro", 200));

        item.marcarComoLido(LocalDate.of(2026, 3, 10));
        assertEquals(1, meta.getQuantidadeAlcancada());

        item.marcarComoLendo();
        assertEquals(0, meta.getQuantidadeAlcancada());
    }

    private ItemEstante itemComLivro(int paginas) {
        ItemEstante item = new ItemEstante();
        item.setLivro(TestFixtures.livro(1L, "Livro", paginas));
        return item;
    }
}
