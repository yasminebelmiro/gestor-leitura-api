package ifgoiano.gestor_leitura_api.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AutorTest {

    @Test
    void deveInformarQuandoNaoExistiremObras() {
        Autor autor = new Autor();

        assertEquals("Nenhuma obra cadastrada.", autor.listarObras());
    }

    @Test
    void deveInformarQuandoListaDeObrasEstiverVazia() {
        Autor autor = new Autor(null, "Autor", List.of());

        assertEquals("Nenhuma obra cadastrada.", autor.listarObras());
    }

    @Test
    void deveListarTitulosSeparadosPorVirgula() {
        Livro primeiro = TestFixtures.livro(1L, "Livro A", 100);
        Livro segundo = TestFixtures.livro(2L, "Livro B", 200);
        Autor autor = new Autor(null, "Autor", List.of(primeiro, segundo));

        assertEquals("Livro A, Livro B", autor.listarObras());
    }

    @Test
    void deveIgnorarObrasComTituloNulo() {
        Livro comTitulo = TestFixtures.livro(1L, "Livro válido", 100);
        Livro semTitulo = TestFixtures.livro(2L, null, 100);
        Autor autor = new Autor(null, "Autor", List.of(comTitulo, semTitulo));

        assertEquals("Livro válido", autor.listarObras());
    }
}
