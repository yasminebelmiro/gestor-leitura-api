package ifgoiano.gestor_leitura_api.dto.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown= true)
 public record VolumeInfo(
    String title, 
    List<String> authors,
    String description,
    String publisher,
    Date publishedDate,
    int pageCount,
    List<String> categories,
    ImageLinks imageLinks
){}