package ifgoiano.gestor_leitura_api.dto.request;

import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import ifgoiano.gestor_leitura_api.model.StatusLeitura;

public record ItemEstanteRequestDTO(Long id, StatusLeitura status, Long estanteId, LivroResponseDTO livro) {

}
