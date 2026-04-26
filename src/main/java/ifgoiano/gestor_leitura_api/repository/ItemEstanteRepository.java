package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.ItemEstante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemEstanteRepository extends JpaRepository<ItemEstante, Long> {

    // Lista os itens de uma estante.
    List<ItemEstante> findByEstanteId(Long estanteId);

    // Lista os itens associados a um livro.
    List<ItemEstante> findByLivroId(Long livroId);

    // Busca item por estante e livro, evitando duplicidade do mesmo livro na mesma estante.
    Optional<ItemEstante> findByEstanteIdAndLivroId(Long estanteId, Long livroId);

    // Verifica se um livro já foi adicionado em uma estante.
    boolean existsByEstanteIdAndLivroId(Long estanteId, Long livroId);

    // Lista itens pelo status geral: LENDO, LIDO, QUERO_LER, ABANDONADO etc.
    List<ItemEstante> findByStatusIgnoreCase(String status);

    // Lista itens de uma estante filtrando pelo status.
    List<ItemEstante> findByEstanteIdAndStatusIgnoreCase(Long estanteId, String status);

    // Lista todos os itens de um leitor filtrando pelo status.
    @Query("SELECT i FROM ItemEstante i WHERE i.estante.leitor.id = :leitorId AND LOWER(i.status) = LOWER(:status)")
    List<ItemEstante> findByLeitorIdAndStatus(@Param("leitorId") Long leitorId, @Param("status") String status);

    // Conta quantos livros de um leitor estão com o status informado.
    @Query("SELECT COUNT(i) FROM ItemEstante i WHERE i.estante.leitor.id = :leitorId AND LOWER(i.status) = LOWER(:status)")
    Long countByLeitorIdAndStatus(@Param("leitorId") Long leitorId, @Param("status") String status);
}
