package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Busca autores pelo nome exato, ignorando letras maiúsculas/minúsculas.
    Optional<Autor> findByNomeIgnoreCase(String nome);

    // Busca autores que contenham parte do nome informado.
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    // Verifica se já existe autor com esse nome.
    boolean existsByNomeIgnoreCase(String nome);
}
