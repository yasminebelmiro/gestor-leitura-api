package ifgoiano.gestor_leitura_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown= true)
public record GoogleBookItem(String id, VolumeInfo volumeInfo){

}



