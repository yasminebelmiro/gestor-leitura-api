package ifgoiano.gestor_leitura_api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ifgoiano.gestor_leitura_api.dto.request.RegistroLeituraRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.model.RegistroLeitura;

@Mapper(componentModel="spring")
public interface RegistroLeituraMapper {

    @Mapping(target="livro", source="item.livro.titulo" )
    @Mapping(target="itemId", source="item.id")
    RegistroLeituraResponseDTO toResponse(RegistroLeitura registro);
    List<RegistroLeituraResponseDTO> tResponseList(List<RegistroLeitura> registros);

    @Mapping(target="id", ignore=true)
    @Mapping(target="item.id", source="idItemEstante")
    RegistroLeitura toEntity(RegistroLeituraRequestDTO dto);
}
