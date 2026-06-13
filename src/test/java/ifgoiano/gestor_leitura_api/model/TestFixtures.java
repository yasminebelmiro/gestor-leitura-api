package ifgoiano.gestor_leitura_api.model;

import java.util.ArrayList;

final class TestFixtures {

    private TestFixtures() {
    }

    static Livro livro(Long id, String titulo, int numeroPaginas) {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setGoogleVolumeId("google-" + (id != null ? id : System.nanoTime()));
        livro.setTitulo(titulo);
        livro.setIsbn("isbn-" + (id != null ? id : System.nanoTime()));
        livro.setNumeroPaginas(numeroPaginas);
        livro.setAutores(new ArrayList<>());
        livro.setGeneros(new ArrayList<>());
        livro.setAvaliacao(new ArrayList<>());
        livro.setDataPublicacao("2025-01-01");
        livro.setCapa_url("https://exemplo.com/capa.jpg");
        return livro;
    }

    static Leitor leitor(Long id, String nickname) {
        Leitor leitor = new Leitor();
        leitor.setId(id);
        leitor.setNickName(nickname);
        leitor.setEmail(nickname + "@email.com");
        leitor.setSenha("12345678");
        leitor.setEstantes(new ArrayList<>());
        leitor.setMetas(new ArrayList<>());
        return leitor;
    }

    static Estante estante(Leitor leitor, String nome) {
        Estante estante = new Estante();
        estante.setNome(nome);
        estante.setLeitor(leitor);
        estante.setItens(new ArrayList<>());
        leitor.getEstantes().add(estante);
        return estante;
    }

    static Resenha resenha(Leitor leitor, double avaliacao, String texto) {
        Resenha resenha = new Resenha();
        resenha.setLeitor(leitor);
        resenha.setAvaliacao(avaliacao);
        resenha.setTexto(texto);
        return resenha;
    }
}
