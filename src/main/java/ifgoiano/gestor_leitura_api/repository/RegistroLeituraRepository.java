package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.RegistroLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroLeituraRepository extends JpaRepository<RegistroLeitura, Long> {

    // Lista todos os registros de leitura de um item de estante.
    List<RegistroLeitura> findByItemEstanteId(Long itemEstanteId);

    // Lista os registros em ordem cronológica.
    List<RegistroLeitura> findByItemEstanteIdOrderByDataAsc(Long itemEstanteId);

    // Busca o registro mais recente de um item de estante.
    Optional<RegistroLeitura> findTopByItemEstanteIdOrderByDataDesc(Long itemEstanteId);

    // Busca registros dentro de um período.
    List<RegistroLeitura> findByDataBetween(Date dataInicio, Date dataFim);

    // Busca a primeira data registrada para um item.
    @Query("SELECT MIN(r.data) FROM RegistroLeitura r WHERE r.itemEstante.id = :itemEstanteId")
    Date findPrimeiraDataByItemEstanteId(@Param("itemEstanteId") Long itemEstanteId);

    // Busca a última data registrada para um item.
    @Query("SELECT MAX(r.data) FROM RegistroLeitura r WHERE r.itemEstante.id = :itemEstanteId")
    Date findUltimaDataByItemEstanteId(@Param("itemEstanteId") Long itemEstanteId);

    // Busca a maior página registrada para um item.
    @Query("SELECT MAX(r.paginaAtual) FROM RegistroLeitura r WHERE r.itemEstante.id = :itemEstanteId")
    Integer findMaiorPaginaAtualByItemEstanteId(@Param("itemEstanteId") Long itemEstanteId);
}