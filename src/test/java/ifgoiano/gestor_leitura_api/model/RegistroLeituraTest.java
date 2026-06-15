package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistroLeituraTest {

    @Test
    void deveAtualizarPaginaEComentario() {
        ItemEstante item = itemComLivro(200);
        RegistroLeitura registro = new RegistroLeitura(null, item, LocalDate.now(), 10, "Inicial");

        registro.atualizarProgresso(80, "Avancei na leitura");

        assertEquals(80, registro.getPaginaAtual());
        assertEquals("Avancei na leitura", registro.getComentarios());
    }

    @Test
    void naoDeveAtualizarRegistroSemItemComLivro() {
        RegistroLeitura registroSemItem = new RegistroLeitura();
        RegistroLeitura registroComItemSemLivro = new RegistroLeitura();
        registroComItemSemLivro.setItem(new ItemEstante());

        IllegalArgumentException primeiroErro = assertThrows(
                IllegalArgumentException.class,
                () -> registroSemItem.atualizarProgresso(10, null)
        );
        IllegalArgumentException segundoErro = assertThrows(
                IllegalArgumentException.class,
                () -> registroComItemSemLivro.atualizarProgresso(10, null)
        );

        assertEquals("O registro deve estar vinculado a um item com livro.", primeiroErro.getMessage());
        assertEquals("O registro deve estar vinculado a um item com livro.", segundoErro.getMessage());
    }

    @Test
    void naoDeveAtualizarParaPaginaNegativa() {
        RegistroLeitura registro = new RegistroLeitura(null, itemComLivro(200), LocalDate.now(), 10, null);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> registro.atualizarProgresso(-1, null)
        );

        assertEquals("A página atual não pode ser menor que zero.", erro.getMessage());
        assertEquals(10, registro.getPaginaAtual());
    }

    @Test
    void naoDeveAtualizarParaPaginaAcimaDoTotalDoLivro() {
        RegistroLeitura registro = new RegistroLeitura(null, itemComLivro(200), LocalDate.now(), 10, null);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> registro.atualizarProgresso(201, null)
        );

        assertEquals("A página atual não pode ultrapassar o total de páginas do livro.", erro.getMessage());
        assertEquals(10, registro.getPaginaAtual());
    }

    @Test
    void deveAceitarPaginaIgualAoTotalDoLivro() {
        RegistroLeitura registro = new RegistroLeitura(null, itemComLivro(200), LocalDate.now(), 10, null);

        registro.atualizarProgresso(200, "Fim");

        assertEquals(200, registro.getPaginaAtual());
    }

    private ItemEstante itemComLivro(int paginas) {
        ItemEstante item = new ItemEstante();
        item.setLivro(TestFixtures.livro(1L, "Livro", paginas));
        return item;
    }
}
