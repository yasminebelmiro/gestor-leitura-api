package ifgoiano.gestor_leitura_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ifgoiano.gestor_leitura_api.model.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    default Leitor findByIdOrThrow(Long id) {
        return findById(id).orElseThrow();
    }
    // Busca leitor pelo e-mail. Útil para login, validação e evitar cadastro duplicado.z
    Leitor findByEmailIgnoreCase(String email);

    // Busca leitor pelo nome exato, ignorando letras maiúsculas/minúsculas.
    Optional<Leitor> findByNickNameIgnoreCase(String nickName);

    // Busca leitores que contenham parte do nome informado.
    List<Leitor> findByNickNameContainingIgnoreCase(String nickName);

    // Verifica se já existe leitor com esse e-mail.
    boolean existsByEmailIgnoreCase(String email);

    //Verifica a existencia de um nickname.
    boolean existsByNickNameIgnoreCase(String nickName);

    //Busca por email e senha
    Optional<Leitor> findByEmailIgnoreCaseAndSenha(String email, String senha);

}