package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.model.ItemEstante;

@Mapper(componentModel = "spring")
public interface ItemEstanteMapper {

    @Mapping(target = "estanteNome", source = "estante.nome")
    @Mapping(target = "livroNome", source = "livro.titulo")
    @Mapping(target="googleVolumeId", source="livro.googleVolumeId")
    ItemEstanteResponseDTO toResponse(ItemEstante item);

    List<ItemEstanteResponseDTO> toResponseList(List<ItemEstante> item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estante", ignore = true)
    @Mapping(target = "registros", ignore = true)
    @Mapping(target = "dataConclusao", ignore = true)
    @Mapping(target = "livro", ignore = true)
    ItemEstante toEntity(ItemEstanteRequestDTO dto);

}
