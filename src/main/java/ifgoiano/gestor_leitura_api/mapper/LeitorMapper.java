package ifgoiano.gestor_leitura_api.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.LeitorRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import ifgoiano.gestor_leitura_api.model.Leitor;

@Mapper(componentModel="spring")
public interface  LeitorMapper {
    LeitorResponseDTO toResponse(Leitor leitor);

    @Mapping(target="id", ignore=true)
    Leitor toEntity(LeitorRequestDTO dto);
}
