package ifgoiano.gestor_leitura_api.dto.request;

import ifgoiano.gestor_leitura_api.model.Estante;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.model.StatusLeitura;

public record ItemEstanteRequestDTO(Long id, StatusLeitura status, Estante estante, Livro livro) {

}
