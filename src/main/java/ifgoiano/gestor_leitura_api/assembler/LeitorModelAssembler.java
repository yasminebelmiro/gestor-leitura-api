package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.LeitorController;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LeitorModelAssembler
        implements RepresentationModelAssembler<LeitorResponseDTO, EntityModel<LeitorResponseDTO>> {

    @Override
    public EntityModel<LeitorResponseDTO> toModel(LeitorResponseDTO dto) {
        EntityModel<LeitorResponseDTO> model = EntityModel.of(dto);

        int anoAtual = LocalDate.now().getYear();

        model.add(linkTo(methodOn(LeitorController.class).findById(dto.id())).withSelfRel());
        model.add(linkTo(methodOn(LeitorController.class).update(dto.id(), null)).withRel("Atualizar Leitor"));
        model.add(linkTo(methodOn(LeitorController.class).delete(dto.id())).withRel("Deletar Leitor"));

        model.add(linkTo(methodOn(LeitorController.class).buscarMetaPorAno(dto.id(), anoAtual)).withRel("Meta do Ano Atual"));
        model.add(linkTo(methodOn(LeitorController.class).calcularProgressoGeral(dto.id(), anoAtual)).withRel("Progresso Geral"));
        model.add(linkTo(methodOn(LeitorController.class).contarLivrosLidosNoAno(dto.id(), anoAtual)).withRel("Contar Livros Lidos no Ano"));

        return model;
    }
}
