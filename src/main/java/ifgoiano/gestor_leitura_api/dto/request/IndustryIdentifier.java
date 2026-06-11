package ifgoiano.gestor_leitura_api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IndustryIdentifier(String type, String identifier) {
}
 