package ifgoiano.gestor_leitura_api.dto.response;

import ifgoiano.gestor_leitura_api.model.Estante;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.model.StatusLeitura;

public record ItemEstanteResponseDTO(Long id, Enum<StatusLeitura> status, String estanteNome, Livro livro) {

}
