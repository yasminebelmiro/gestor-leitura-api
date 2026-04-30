package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {

    // // Busca editora pelo nome exato, ignorando letras maiúsculas/minúsculas.
    // Optional<Editora> findByNomeIgnoreCase(String nome);

    // // Busca editoras que contenham parte do nome informado.
    // List<Editora> findByNomeContainingIgnoreCase(String nome);

    // // Verifica se já existe editora com esse nome.
    // boolean existsByNomeIgnoreCase(String nome);
}
