package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    // // Busca leitor pelo e-mail. Útil para login, validação e evitar cadastro duplicado.
    // Optional<Leitor> findByEmail(String email);

    // // Busca leitor pelo nome exato, ignorando letras maiúsculas/minúsculas.
    // Optional<Leitor> findByNomeIgnoreCase(String nome);

    // // Busca leitores que contenham parte do nome informado.
    // List<Leitor> findByNomeContainingIgnoreCase(String nome);

    // // Verifica se já existe leitor com esse e-mail.
    // boolean existsByEmail(String email);
}