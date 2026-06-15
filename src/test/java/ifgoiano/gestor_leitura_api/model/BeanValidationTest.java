package ifgoiano.gestor_leitura_api.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void iniciarValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void fecharValidator() {
        validatorFactory.close();
    }

    @Test
    void deveValidarNomeDoAutor() {
        Autor autor = new Autor();
        autor.setNome(" ");
        assertMensagem(autor, "nome", "O nome do autor é obrigatório.");

        autor.setNome("a".repeat(151));
        assertMensagem(autor, "nome", "O nome do autor deve ter no máximo 150 caracteres.");
    }

    @Test
    void deveValidarNomeDaEditora() {
        Editora editora = new Editora();
        editora.setNome("");
        assertMensagem(editora, "nome", "O nome da editora é obrigatório.");

        editora.setNome("a".repeat(151));
        assertMensagem(editora, "nome", "O nome da editora deve ter no máximo 150 caracteres.");
    }

    @Test
    void deveValidarEstante() {
        Estante estante = new Estante();
        estante.setNome(" ");
        assertMensagem(estante, "nome", "O nome da estante é obrigatório.");

        estante.setNome("a".repeat(121));
        assertMensagem(estante, "nome", "O nome da estante deve ter no máximo 120 caracteres.");

        estante.setLeitor(null);
        assertMensagem(estante, "leitor", "A estante deve pertencer a um leitor.");
    }

    @Test
    void deveValidarGenero() {
        Genero genero = new Genero();
        genero.setNome("");
        assertMensagem(genero, "nome", "O nome do gênero é obrigatório.");

        genero.setNome("a".repeat(101));
        assertMensagem(genero, "nome", "O nome do gênero deve ter no máximo 100 caracteres.");

        genero.setDescricao(" ");
        assertMensagem(genero, "descricao", "A descrição do gênero é obrigatória.");

        genero.setDescricao("a".repeat(501));
        assertMensagem(genero, "descricao", "A descrição do gênero deve ter no máximo 500 caracteres.");
    }

    @Test
    void deveValidarItemEstante() {
        ItemEstante item = new ItemEstante();
        item.setEstante(null);
        item.setLivro(null);

        assertMensagem(item, "estante", "O item deve pertencer a uma estante.");
        assertMensagem(item, "livro", "O item deve estar vinculado a um livro.");
    }

    @Test
    void deveValidarLeitor() {
        Leitor leitor = new Leitor();

        leitor.setNickName(" ");
        assertMensagem(leitor, "nickName", "O nickname é obrigatório.");

        leitor.setNickName("ab");
        assertMensagem(leitor, "nickName", "O nickname deve ter entre 3 e 80 caracteres.");

        leitor.setNickName("a".repeat(81));
        assertMensagem(leitor, "nickName", "O nickname deve ter entre 3 e 80 caracteres.");

        leitor.setEmail("email-invalido");
        assertMensagem(leitor, "email", "Informe um e-mail válido.");

        leitor.setEmail("a".repeat(171) + "@email.com");
        assertMensagem(leitor, "email", "O e-mail deve ter no máximo 180 caracteres.");

        leitor.setSenha("1234567");
        assertMensagem(leitor, "senha", "A senha deve ter no mínimo 8 caracteres.");
    }

    @Test
    void deveValidarLivroExcetoConfiguracaoDaDataPublicacao() {
        Livro livro = new Livro();

        livro.setGoogleVolumeId(" ");
        assertMensagem(livro, "googleVolumeId", "O identificador do Google Books é obrigatório.");

        livro.setGoogleVolumeId("a".repeat(101));
        assertMensagem(livro, "googleVolumeId", "O identificador do Google Books deve ter no máximo 100 caracteres.");

        livro.setTitulo("");
        assertMensagem(livro, "titulo", "O título do livro é obrigatório.");

        livro.setTitulo("a".repeat(256));
        assertMensagem(livro, "titulo", "O título deve ter no máximo 255 caracteres.");

//        livro.setIsbn(" ");
//        assertMensagem(livro, "isbn", "O ISBN é obrigatório.");
//
//        livro.setIsbn("a".repeat(21));
//        assertMensagem(livro, "isbn", "O ISBN deve ter no máximo 20 caracteres.");

        livro.setNumeroPaginas(0);
        assertMensagem(livro, "numeroPaginas", "O número de páginas deve ser maior que zero.");

        livro.setCapa_url("");
        assertMensagem(livro, "capa_url", "A URL da capa é obrigatória.");

        livro.setCapa_url("a".repeat(1001));
        assertMensagem(livro, "capa_url", "A URL da capa deve ter no máximo 1000 caracteres.");
    }

    @Test
    void deveValidarMetaAnual() {
        MetaAnual meta = new MetaAnual();

        meta.setAno(1899);
        assertMensagem(meta, "ano", "O ano da meta é inválido.");

        meta.setAno(2101);
        assertMensagem(meta, "ano", "O ano da meta é inválido.");

        meta.setQuantidadeAlvo(0);
        assertMensagem(meta, "quantidadeAlvo", "A quantidade alvo deve ser maior que zero.");

        meta.setQuantidadeAlcancada(-1);
        assertMensagem(meta, "quantidadeAlcancada", "A quantidade alcançada não pode ser negativa.");

        meta.setLeitor(null);
        assertMensagem(meta, "leitor", "A meta anual deve pertencer a um leitor.");
    }

    @Test
    void deveValidarRegistroLeitura() {
        RegistroLeitura registro = new RegistroLeitura();

        registro.setData(null);
        assertMensagem(registro, "data", "A data do registro é obrigatória.");

        registro.setPaginaAtual(-1);
        assertMensagem(registro, "paginaAtual", "A página atual não pode ser menor que zero.");

        registro.setComentarios("a".repeat(1001));
        assertMensagem(registro, "comentarios", "O comentário deve ter no máximo 1000 caracteres.");

        registro.setItem(null);
        assertMensagem(registro, "item", "O registro deve pertencer a um item de estante.");
    }

    @Test
    void deveValidarResenha() {
        Resenha resenha = new Resenha();

        resenha.setTexto(" ");
        assertMensagem(resenha, "texto", "O texto da resenha é obrigatório.");

        resenha.setTexto("a".repeat(2001));
        assertMensagem(resenha, "texto", "A resenha deve ter no máximo 2000 caracteres.");

        resenha.setAvaliacao(null);
        assertMensagem(resenha, "avaliacao", "A avaliação é obrigatória.");

        resenha.setAvaliacao(-0.1);
        assertMensagem(resenha, "avaliacao", "A avaliação mínima é 0.");

        resenha.setAvaliacao(5.1);
        assertMensagem(resenha, "avaliacao", "A avaliação máxima é 5.");

        resenha.setLeitor(null);
        assertMensagem(resenha, "leitor", "A resenha deve possuir um leitor.");

        resenha.setLivro(null);
        assertMensagem(resenha, "livro", "A resenha deve possuir um livro.");
    }

    private void assertMensagem(Object objeto, String propriedade, String mensagemEsperada) {
        Set<String> mensagens = validator.validateProperty(objeto, propriedade).stream()
                .map(violacao -> violacao.getMessage())
                .collect(Collectors.toSet());

        assertTrue(
                mensagens.contains(mensagemEsperada),
                () -> "Mensagem esperada: '" + mensagemEsperada + "'. Mensagens encontradas: " + mensagens
        );
    }
}
