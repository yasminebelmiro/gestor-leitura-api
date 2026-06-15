package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LivroTest {

    @Test
    void deveRetornarZeroQuandoNaoHouverAvaliacoes() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);

        assertEquals(0.0, livro.calcularMediaAvaliacoes(), 0.0001);
    }

    @Test
    void deveCalcularMediaDasAvaliacoesIgnorandoValoresNulos() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);
        Resenha primeira = new Resenha();
        primeira.setAvaliacao(4.0);
        Resenha segunda = new Resenha();
        segunda.setAvaliacao(2.0);
        Resenha semNota = new Resenha();
        semNota.setAvaliacao(null);
        livro.setAvaliacao(List.of(primeira, segunda, semNota));

        assertEquals(3.0, livro.calcularMediaAvaliacoes(), 0.0001);
    }

    @Test
    void deveExibirFichaTecnicaCompleta() {
        Livro livro = TestFixtures.livro(1L, "Dom Casmurro", 256);
        livro.setIsbn("9780000000000");
        livro.setDataPublicacao("1899-01-01");
        livro.setAutores(List.of(new Autor(1L, "Machado de Assis", null)));
        livro.setGeneros(List.of(new Genero(1L, "Romance", "Romance brasileiro")));
        livro.setEditora(new Editora(1L, "Editora Exemplo", null));
        Resenha resenha = new Resenha();
        resenha.setAvaliacao(5.0);
        livro.setAvaliacao(List.of(resenha));

        String ficha = livro.exibirFichaTecnicaCompleta();

        assertEquals(
                "Título: Dom Casmurro\n" +
                        "ISBN: 9780000000000\n" +
                        "Autores: Machado de Assis\n" +
                        "Editora: Editora Exemplo\n" +
                        "Gêneros: Romance\n" +
                        "Número de páginas: 256\n" +
                        "Data de publicação: 1899-01-01\n" +
                        "Média de avaliações: 5.0",
                ficha
        );
    }

    @Test
    void fichaTecnicaDeveIndicarDadosRelacionadosNaoInformados() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);
        livro.setAutores(new ArrayList<>());
        livro.setGeneros(new ArrayList<>());
        livro.setEditora(null);

        String ficha = livro.exibirFichaTecnicaCompleta();

        org.junit.jupiter.api.Assertions.assertTrue(ficha.contains("Autores: Não informado"));
        org.junit.jupiter.api.Assertions.assertTrue(ficha.contains("Editora: Não informada"));
        org.junit.jupiter.api.Assertions.assertTrue(ficha.contains("Gêneros: Não informado"));
    }

    @Test
    void deveAdicionarResenhaEVincularLivro() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        Resenha resenha = TestFixtures.resenha(leitor, 4.5, "Boa leitura");

        livro.adicionarResenha(resenha);

        assertSame(livro, resenha.getLivro());
        assertEquals(1, livro.getAvaliacao().size());
        assertSame(resenha, livro.getAvaliacao().get(0));
    }

    @Test
    void naoDeveAdicionarResenhaNula() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> livro.adicionarResenha(null)
        );

        assertEquals("A resenha não pode ser nula.", erro.getMessage());
    }

    @Test
    void naoDeveAdicionarResenhaSemLeitor() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);
        Resenha resenha = new Resenha();

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> livro.adicionarResenha(resenha)
        );

        assertEquals("A resenha deve possuir um leitor.", erro.getMessage());
    }

    @Test
    void naoDevePermitirDuasResenhasDoMesmoLeitor() {
        Livro livro = TestFixtures.livro(1L, "Livro", 200);
        Leitor leitor = TestFixtures.leitor(1L, "artur");
        livro.adicionarResenha(TestFixtures.resenha(leitor, 4.0, "Primeira"));

        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> livro.adicionarResenha(TestFixtures.resenha(leitor, 5.0, "Segunda"))
        );

        assertEquals("O leitor já possui uma resenha para este livro.", erro.getMessage());
        assertEquals(1, livro.getAvaliacao().size());
    }
}
