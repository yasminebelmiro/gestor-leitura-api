package ifgoiano.gestor_leitura_api.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ifgoiano.gestor_leitura_api.model.ItemEstante;
import ifgoiano.gestor_leitura_api.model.StatusLeitura;

@Repository
public interface ItemEstanteRepository extends JpaRepository<ItemEstante, Long> {

    // Lista os itens de uma estante.
    List<ItemEstante> findByEstanteId(Long estanteId);

    // Lista os itens associados a um livro.
    List<ItemEstante> findByLivroId(Long livroId);

    // Busca item por estante e livro, evitando duplicidade do mesmo livro na mesma
    // estante.
    Optional<ItemEstante> findByEstanteIdAndLivroId(Long estanteId, Long livroId);

    // Verifica se um livro já foi adicionado em uma estante.
    boolean existsByEstanteIdAndLivroId(Long estanteId, Long livroId);

    // Lista itens pelo status geral: LENDO, LIDO, QUERO_LER, ABANDONADO etc.
    List<ItemEstante> findByStatus(Enum<StatusLeitura> status);

    // Lista itens de uma estante filtrando pelo status.
    List<ItemEstante> findByEstanteIdAndStatus(Long estanteId, Enum<StatusLeitura> status);

    // Conta estantes por id
    long countByEstanteId(Long estanteId);

    // Conta os livros por id
    long countByLivroId(Long livroId);

    // Lista todos os itens de um leitor filtrando pelo status.
    @Query("SELECT i FROM ItemEstante i WHERE i.estante.leitor.id = :leitorId AND LOWER(i.status) = LOWER(:status)")
    List<ItemEstante> findByLeitorIdAndStatus(@Param("leitorId") Long leitorId, @Param("status") String status);

    // Conta quantos livros de um leitor estão com o status informado.
    @Query("""
            SELECT COUNT(i)
            FROM ItemEstante i
            WHERE i.estante.leitor.id = :leitorId
              AND i.status = :status
            """)
    long countByLeitorIdAndStatus(
            @Param("leitorId") Long leitorId,
            @Param("status") Enum<StatusLeitura> status);

    // Conta quantos itens da estante de um leitor têm determinado status e
    // pertencem a livros publicados entre duas datas.
    @Query("""
            SELECT COUNT(i)
            FROM ItemEstante i
            WHERE i.estante.leitor.id = :leitorId
              AND i.status = :status
              AND i.livro.dataPublicacao BETWEEN :inicio AND :fim
            """)
    long countByLeitorIdAndStatusAndLivroPublicadoNoPeriodo(
            @Param("leitorId") Long leitorId,
            @Param("status") Enum<StatusLeitura> status,
            @Param("inicio") Date inicio,
            @Param("fim") Date fim);

}
