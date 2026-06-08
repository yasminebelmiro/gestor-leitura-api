package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.EstanteResquestDTO;
import ifgoiano.gestor_leitura_api.dto.response.EstanteResponseDTO;
import ifgoiano.gestor_leitura_api.model.Estante;

@Mapper(componentModel = "spring")
public interface EstanteMapper {
    @Mapping(target = "leitorNickName", source = "leitor.nickName")
    EstanteResponseDTO toResponse(Estante estante);

    List<EstanteResponseDTO> toResponseList(List<Estante> estantes);

    @Mapping(target = "id", ignore = true)
    Estante toEntity(EstanteResquestDTO dto);

}
