package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    // Busca gênero pelo nome exato, ignorando letras maiúsculas/minúsculas.
    Optional<Genero> findByNomeIgnoreCase(String nome);

    // Busca gêneros que contenham parte do nome informado.
    List<Genero> findByNomeContainingIgnoreCase(String nome);

    // Busca gêneros pela descrição.
    List<Genero> findByDescricaoContainingIgnoreCase(String descricao);

    // Verifica se já existe gênero com esse nome.
    boolean existsByNomeIgnoreCase(String nome);
}
