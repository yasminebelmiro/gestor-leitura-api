package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.ResenhaController;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResenhaModelAssembler implements RepresentationModelAssembler<ResenhaResponseDTO, EntityModel<ResenhaResponseDTO>> {

    @Override
    public EntityModel<ResenhaResponseDTO> toModel(ResenhaResponseDTO dto) {
        EntityModel<ResenhaResponseDTO> model = EntityModel.of(dto);
        Long id = dto.id();
        
        model.add(linkTo(methodOn(ResenhaController.class).findById(id)).withSelfRel());
        model.add(linkTo(methodOn(ResenhaController.class).update(id, null)).withRel("atualizar"));
        model.add(linkTo(methodOn(ResenhaController.class).delete(id)).withRel("excluir"));

        model.add(linkTo(methodOn(ResenhaController.class).listByLivro(dto.livroId())).withRel("resenhas_do_livro"));

        return model;
    }

    @Override
    public CollectionModel<EntityModel<ResenhaResponseDTO>> toCollectionModel(Iterable<? extends ResenhaResponseDTO> entities) {
        List<EntityModel<ResenhaResponseDTO>> models = ((List<ResenhaResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}