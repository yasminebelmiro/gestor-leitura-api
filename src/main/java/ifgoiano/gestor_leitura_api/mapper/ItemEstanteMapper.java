package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.model.ItemEstante;

public interface ItemEstanteMapper {
    ItemEstanteResponseDTO toResponse(ItemEstante item);

    List<ItemEstanteResponseDTO> toResponseList(List<ItemEstante> item);

    @Mapping(target="id", ignore=true)
    ItemEstante toEntity(ItemEstanteRequestDTO dto);

}
