package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Busca livro pelo ISBN.
    Optional<Livro> findByIsbn(String isbn);

    // Verifica se já existe livro com esse ISBN.
    boolean existsByIsbn(String isbn);

    // Busca livros que contenham parte do título informado.
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // Busca livros por editora.
    List<Livro> findByEditoraId(Long editoraId);

    // Busca livros publicados dentro de um intervalo de datas.
    List<Livro> findByDataPublicacaoBetween(Date dataInicio, Date dataFim);

    // Lista os livros publicados em determinado ano.
    @Query("SELECT l FROM Livro l WHERE YEAR(l.dataPublicacao) = :ano")
    List<Livro> findLancamentosDoAno(@Param("ano") int ano);

    // Lista livros de um determinado autor.
    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.id = :autorId")
    List<Livro> findLivrosByAutorId(@Param("autorId") Long autorId);

    // Lista livros de um determinado gênero.
    @Query("SELECT l FROM Livro l JOIN l.generos g WHERE g.id = :generoId")
    List<Livro> findLivrosByGeneroId(@Param("generoId") Long generoId);
}
