package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstanteTest {

    @Test
    void deveAdicionarLivroComStatusQueroLerEVinculosCorretos() {
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Estante estante = TestFixtures.estante(leitor, "Principal");
        Livro livro = TestFixtures.livro(1L, "Livro", 300);

        ItemEstante item = estante.adicionarLivro(livro);

        assertAllItemCriado(estante, livro, item);
    }

    private void assertAllItemCriado(Estante estante, Livro livro, ItemEstante item) {
        assertSame(estante, item.getEstante());
        assertSame(livro, item.getLivro());
        assertEquals(StatusLeitura.QUERO_LER, item.getStatus());
        assertEquals(1, estante.getItens().size());
        assertSame(item, estante.getItens().get(0));
    }

    @Test
    void naoDeveAdicionarLivroNulo() {
        Estante estante = TestFixtures.estante(TestFixtures.leitor(1L, "artur"), "Principal");

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> estante.adicionarLivro(null)
        );

        assertEquals("O livro não pode ser nulo.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarMesmoLivroDuasVezes() {
        Estante estante = TestFixtures.estante(TestFixtures.leitor(1L, "artur"), "Principal");
        Livro livro = TestFixtures.livro(1L, "Livro", 300);
        estante.adicionarLivro(livro);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> estante.adicionarLivro(livro)
        );

        assertEquals("Este livro já está nesta estante.", erro.getMessage());
        assertEquals(1, estante.getItens().size());
    }

    @Test
    void deveSomarPaginasDosLivrosDaEstante() {
        Estante estante = TestFixtures.estante(TestFixtures.leitor(1L, "artur"), "Principal");
        estante.adicionarLivro(TestFixtures.livro(1L, "A", 100));
        estante.adicionarLivro(TestFixtures.livro(2L, "B", 250));

        assertEquals(350, estante.calcularTotalPaginas());
    }

    @Test
    void deveRetornarZeroQuandoNaoHouverItens() {
        Estante estante = TestFixtures.estante(TestFixtures.leitor(1L, "artur"), "Principal");

        assertEquals(0, estante.calcularTotalPaginas());
    }

    @Test
    void deveIgnorarItemSemLivroNoCalculoDePaginas() {
        Estante estante = TestFixtures.estante(TestFixtures.leitor(1L, "artur"), "Principal");
        ItemEstante semLivro = new ItemEstante();
        semLivro.setEstante(estante);
        estante.getItens().add(semLivro);
        estante.adicionarLivro(TestFixtures.livro(1L, "A", 100));

        assertEquals(100, estante.calcularTotalPaginas());
    }

    @Test
    void deveConverterListaNulaDeItensEmListaVazia() {
        Estante estante = new Estante();

        estante.setItens(null);

        assertTrue(estante.getItens().isEmpty());
        assertTrue(estante.getItens() instanceof ArrayList);
    }
}
