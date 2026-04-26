package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.MetaAnual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaAnualRepository extends JpaRepository<MetaAnual, Long> {

    // Lista todas as metas de um leitor.
    List<MetaAnual> findByLeitorId(Long leitorId);

    // Busca a meta de um leitor em um ano específico.
    Optional<MetaAnual> findByLeitorIdAndAno(Long leitorId, int ano);

    // Verifica se o leitor já cadastrou meta para esse ano.
    boolean existsByLeitorIdAndAno(Long leitorId, int ano);

    // Lista metas de todos os leitores para determinado ano.
    List<MetaAnual> findByAno(int ano);

    // Verifica se a meta anual de um leitor foi batida.
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MetaAnual m WHERE m.leitor.id = :leitorId AND m.ano = :ano AND m.quantidadeAlcancada >= m.quantidadeAlvo")
    boolean isMetaBatida(@Param("leitorId") Long leitorId, @Param("ano") int ano);
}
