package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Autor;
import ifgoiano.gestor_leitura_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // // Busca autores pelo nome exato, ignorando letras maiúsculas/minúsculas.
    // Optional<Autor> findByNomeIgnoreCase(String nome);

    // // Busca autores que contenham parte do nome informado.
    // List<Autor> findByNomeContainingIgnoreCase(String nome);

    // // Verifica se já existe autor com esse nome.
    // boolean existsByNomeIgnoreCase(String nome);

    // Busca livro por id de autor.
//    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.id = :autorId")
//    List<Livro> findObrasByAutorId(@Param("autorId") Long autorId);

    // Busca lançamentos de um autor em um determinado periodo.
//    @Query("""
//           SELECT l FROM Livro l
//           JOIN l.autores a
//           WHERE a.id = :autorId
//             AND l.dataPublicacao BETWEEN :inicio AND :fim
//           """)
//    List<Livro> findLancamentosDoAutorNoPeriodo(
//            @Param("autorId") Long autorId,
//            @Param("inicio") Date inicio,
//            @Param("fim") Date fim
//    );
}
