package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Estante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstanteRepository extends JpaRepository<Estante, Long> {

    // // Lista todas as estantes de um leitor.
    // List<Estante> findByLeitorId(Long leitorId);

    // // Busca uma estante específica de um leitor.
    // Optional<Estante> findByIdAndLeitorId(Long id, Long leitorId);

    // // Busca estantes de um leitor pelo nome.
    // List<Estante> findByLeitorIdAndNomeContainingIgnoreCase(Long leitorId, String nome);

    // Busca estante pelo nome da estante
    //List<Estante> findByNomeContainingIgnoreCase(String nome);

    // // Verifica se o leitor já possui uma estante com esse nome.
    // boolean existsByNomeIgnoreCaseAndLeitorId(String nome, Long leitorId);

    //tras a soma das estantes de um leitor
    //long countByLeitorId(Long leitorId);

    // // Calcula o total de páginas dos livros contidos em uma estante.
    // @Query("""
    //           SELECT COALESCE(SUM(i.livro.numeroPaginas), 0)
    //           FROM ItemEstante i
    //           WHERE i.estante.id = :estanteId
    //           """)
    //    Long calcularTotalPaginasDaEstante(@Param("estanteId") Long estanteId);
}
