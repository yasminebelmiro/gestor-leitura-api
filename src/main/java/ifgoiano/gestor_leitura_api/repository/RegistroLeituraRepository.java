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
     List<RegistroLeitura> findByItemId(Long itemId);

     // Lista os registros em ordem cronológica.
     List<RegistroLeitura> findByItemIdOrderByDataAsc(Long itemId);

     // lista os registros em ordem decrescente
    List<RegistroLeitura> findByItemIdOrderByDataDesc(Long itemId);

    // Busca o registro mais recente de um item de estante.
    Optional<RegistroLeitura> findTopByItemIdOrderByDataAsc(Long itemId);

    // Busca registro mais antigo na estante.
    Optional<RegistroLeitura> findTopByItemIdOrderByDataDesc(Long itemId);

     // Busca registros dentro de um período.
     List<RegistroLeitura> findByDataBetween(Date dataInicio, Date dataFim);

     // Busca a primeira data registrada para um item.
     @Query("SELECT MIN(r.data) FROM RegistroLeitura r WHERE r.item.id = :itemId")
     Date findPrimeiraDataByItemId(@Param("itemId") Long itemId);

     // Busca a última data registrada para um item.
     @Query("SELECT MAX(r.data) FROM RegistroLeitura r WHERE r.item.id = :itemId")
     Date findUltimaDataByItemId(@Param("itemId") Long itemId);

     // Busca a maior página registrada para um item.
     @Query("SELECT MAX(r.paginaAtual) FROM RegistroLeitura r WHERE r.item.id = :itemId")
     Integer findMaiorPaginaAtualByItemId(@Param("itemId") Long itemId);
}