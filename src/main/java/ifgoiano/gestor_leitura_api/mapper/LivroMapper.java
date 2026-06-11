package ifgoiano.gestor_leitura_api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import ifgoiano.gestor_leitura_api.model.Autor;
import ifgoiano.gestor_leitura_api.model.Editora;
import ifgoiano.gestor_leitura_api.model.Genero;
import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.repository.AutorRepository;
import ifgoiano.gestor_leitura_api.repository.EditoraRepository;
import ifgoiano.gestor_leitura_api.repository.GeneroRepository;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {
    @Autowired
    protected AutorRepository autorRepository;

    @Autowired
    protected GeneroRepository generoRepository;

    @Autowired
    protected EditoraRepository editoraRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "googleVolumeId", source = "googleId")
    @Mapping(target = "capa_url", source = "capaUrl")
    @Mapping(target = "avaliacao", ignore = true)
    public abstract Livro toEntity(LivroResponseDTO dto);

    protected List<Autor> mapAutores(List<String> nomes) {
        if (nomes == null || nomes.isEmpty())
            return new ArrayList<>();

        List<Autor> lista = new ArrayList<>();
        for (String nome : nomes) {
            Autor autor = autorRepository.findByNomeIgnoreCase(nome)
                    .orElseGet(() -> autorRepository.save(new Autor(null, nome, null)));
            lista.add(autor);
        }
        return lista;
    }

    protected List<Genero> mapGeneros(List<String> nomes) {
        if (nomes == null || nomes.isEmpty())
            return new ArrayList<>();

        List<Genero> lista = new ArrayList<>();
        for (String nome : nomes) {
            Genero genero = generoRepository.findByNomeIgnoreCase(nome)
                    .orElseGet(() -> generoRepository.save(new Genero(null, nome, "Descrição indisponível")));
            lista.add(genero);
        }
        return lista;
    }

    protected Editora mapEditora(String nome) {
        if (nome == null || nome.isEmpty())
            return null;

        return editoraRepository.findByNomeIgnoreCase(nome)
                .orElseGet(() -> editoraRepository.save(new Editora(null, nome, null)));
    }
}