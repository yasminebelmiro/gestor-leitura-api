package ifgoiano.gestor_leitura_api.repository;

import ifgoiano.gestor_leitura_api.model.Editora;
import ifgoiano.gestor_leitura_api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    //Busca obras de uma editora.
//    @Query("SELECT l FROM Livro l WHERE l.editora.id = :editoraId")
//    List<Livro> findObrasByEditoraId(@Param("editoraId") Long editoraId);

    //Busca livros de uma editora em um determinado periodo.
//    @Query("""
//           SELECT l FROM Livro l
//           WHERE l.editora.id = :editoraId
//             AND l.dataPublicacao BETWEEN :inicio AND :fim
//           """)
//    List<Livro> findLancamentosDaEditoraNoPeriodo(
//            @Param("editoraId") Long editoraId,
//            @Param("inicio") Date inicio,
//            @Param("fim") Date fim
//    );

}
