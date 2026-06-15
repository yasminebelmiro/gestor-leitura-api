package ifgoiano.gestor_leitura_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ifgoiano.gestor_leitura_api.exceptions.RegistroLeitutraNotfoundException;
import ifgoiano.gestor_leitura_api.exceptions.ResenhaNotFoundException;
import ifgoiano.gestor_leitura_api.model.RegistroLeitura;
import ifgoiano.gestor_leitura_api.model.Resenha;

@Repository
public interface ResenhaRepository extends JpaRepository<Resenha, Long> {
    default Resenha findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResenhaNotFoundException(id));
    }

    // Lista resenhas feitas por um leitor.
    List<Resenha> findByLeitorId(Long leitorId);

    // Lista resenhas de um livro.
    List<Resenha> findByLivroId(Long livroId);

    // Busca uma resenha específica de um leitor para um livro.
    Optional<Resenha> findByLeitorIdAndLivroId(Long leitorId, Long livroId);

    // Verifica se o leitor já avaliou determinado livro.
    boolean existsByLeitorIdAndLivroId(Long leitorId, Long livroId);

    // Calcula a média de avaliações de um livro.
    @Query("SELECT AVG(r.avaliacao) FROM Resenha r WHERE r.livro.id = :livroId")
    Double calcularMediaAvaliacoesByLivroId(@Param("livroId") Long livroId);

    // Conta quantas avaliações existem para um livro.
    Long countByLivroId(Long livroId);
}
