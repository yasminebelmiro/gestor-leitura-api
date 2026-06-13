package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EditoraTest {

    @Test
    void deveInformarQuandoNaoExistiremObrasNoCatalogo() {
        Editora editora = new Editora();

        assertEquals("Nenhuma obra cadastrada para esta editora.", editora.catalogo());
    }

    @Test
    void deveListarCatalogoSeparadoPorVirgula() {
        Livro primeiro = TestFixtures.livro(1L, "Livro A", 100);
        Livro segundo = TestFixtures.livro(2L, "Livro B", 200);
        Editora editora = new Editora(null, "Editora", List.of(primeiro, segundo));

        assertEquals("Livro A, Livro B", editora.catalogo());
    }

    @Test
    void deveIgnorarObraComTituloNuloNoCatalogo() {
        Livro comTitulo = TestFixtures.livro(1L, "Livro válido", 100);
        Livro semTitulo = TestFixtures.livro(2L, null, 100);
        Editora editora = new Editora(null, "Editora", List.of(comTitulo, semTitulo));

        assertEquals("Livro válido", editora.catalogo());
    }

    @Test
    void deveConverterListaNulaEmListaVaziaAoUsarSetter() {
        Editora editora = new Editora();

        editora.setObras(null);

        assertEquals(List.of(), editora.getObras());
    }
}
