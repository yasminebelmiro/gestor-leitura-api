package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.model.Resenha;

@Mapper(componentModel = "spring")
public interface ResenhaMapper {
    ResenhaResponseDTO toResponse(Resenha resenha);

    List<ResenhaResponseDTO> toListResponse(List<Resenha> resenha);

    @Mapping(target="id", ignore=true)
    Resenha toEntity(ResenhaRequestDTO dto);
}
