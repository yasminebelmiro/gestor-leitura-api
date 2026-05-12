package ifgoiano.gestor_leitura_api.dto.request;

import java.util.Date;
import java.util.List;

import ifgoiano.gestor_leitura_api.model.Autor;
import ifgoiano.gestor_leitura_api.model.Editora;
import ifgoiano.gestor_leitura_api.model.Genero;

public record LivroRequestDTO(String googleId,
        String titulo,
        List<Autor> autores,
        String descricao,
        Editora editora,
        Date dataPublicacao,
        int numeroPaginas,
        List<Genero> generos,
        String capaUrl) {

}
