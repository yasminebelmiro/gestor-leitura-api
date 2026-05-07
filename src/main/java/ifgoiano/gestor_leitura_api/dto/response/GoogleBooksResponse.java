package ifgoiano.gestor_leitura_api.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ifgoiano.gestor_leitura_api.dto.request.GoogleBookItem;

@JsonIgnoreProperties(ignoreUnknown= true)
public record GoogleBooksResponse (List<GoogleBookItem> items){

}

