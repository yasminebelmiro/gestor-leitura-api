package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.MetaAnualRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.model.MetaAnual;

@Mapper(componentModel="spring")
public interface MetaAnualMapper {

    @Mapping(target="leitorId", source="meta.leitor.id")
    MetaAnualResponseDTO toResponse(MetaAnual meta);

    List<MetaAnualResponseDTO> toListResponse(List<MetaAnual> meta);

    @Mapping(target="id", ignore=true)
    MetaAnual toEntity(MetaAnualRequestDTO dto);
    
}
