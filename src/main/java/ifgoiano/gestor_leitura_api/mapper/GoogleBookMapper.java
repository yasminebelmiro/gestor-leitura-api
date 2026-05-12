package ifgoiano.gestor_leitura_api.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ifgoiano.gestor_leitura_api.dto.request.GoogleBookItem;
import ifgoiano.gestor_leitura_api.model.Autor;
import ifgoiano.gestor_leitura_api.model.Genero;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.repository.AutorRepository;
import ifgoiano.gestor_leitura_api.repository.GeneroRepository;


@Mapper(componentModel = "spring")
public abstract class GoogleBookMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Autowired
    protected GeneroRepository generoRepository;

    @Mapping(target = "id", ignore = true) 
    @Mapping(target = "googleVolumeId", source = "id") 
    @Mapping(target = "titulo", source = "volumeInfo.title")
    @Mapping(target = "autores", source = "volumeInfo.authors") 
    @Mapping(target = "generos", source = "volumeInfo.categories")
    public abstract Livro toEntity(GoogleBookItem item);

    protected List<Autor> mapAutores(List<String> nomesAutores) {
        if (nomesAutores == null || nomesAutores.isEmpty()) {
            return new ArrayList<>();
        }

        return nomesAutores.stream()
                .map(nome -> autorRepository.findByNomeIgnoreCase(nome)
                        .orElseGet(() -> {
                            Autor novo = new Autor();
                            novo.setNome(nome);
                            return autorRepository.save(novo);
                        }))
                .collect(Collectors.toList());
    }

    protected List<Genero> mapGeneros(List<String> categorias) {
        if (categorias == null || categorias.isEmpty()) {
            return new ArrayList<>();
        }

        return categorias.stream()
                .map(nome -> generoRepository.findByNomeIgnoreCase(nome)
                        .orElseGet(() -> {
                            Genero novo = new Genero();
                            novo.setNome(nome);
                            return generoRepository.save(novo);
                        }))
                .collect(Collectors.toList());
    }
}