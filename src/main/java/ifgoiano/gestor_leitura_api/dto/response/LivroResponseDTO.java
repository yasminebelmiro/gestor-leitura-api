package ifgoiano.gestor_leitura_api.dto.response;

import java.util.Date;
import java.util.List;

public record LivroResponseDTO(
        String googleId,
        String titulo,
        List<String> autores,
        String descricao,
        String editora,
        Date dataPublicacao,
        int numeroPaginas,
        List<String> generos,
        String capaUrl) {

}
