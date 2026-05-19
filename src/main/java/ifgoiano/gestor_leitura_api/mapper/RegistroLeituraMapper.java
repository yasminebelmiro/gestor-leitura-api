package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.RegistroLeituraRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.model.RegistroLeitura;

@Mapper(componentModel="spring")
public interface RegistroLeituraMapper {
    RegistroLeituraResponseDTO toResponse(RegistroLeitura registro);
    List<RegistroLeituraResponseDTO> tResponseList(List<RegistroLeitura> registros);

    @Mapping(target="id", ignore=true)
    RegistroLeitura toEntity(RegistroLeituraRequestDTO dto);
}
