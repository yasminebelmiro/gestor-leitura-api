package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.EstanteController;
import ifgoiano.gestor_leitura_api.controller.ItemEstanteController;
import ifgoiano.gestor_leitura_api.dto.response.EstanteResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstanteModelAssembler implements RepresentationModelAssembler<EstanteResponseDTO, EntityModel<EstanteResponseDTO>> {

    @Override
    public EntityModel<EstanteResponseDTO> toModel(EstanteResponseDTO dto) {
        EntityModel<EstanteResponseDTO> model = EntityModel.of(dto);


        model.add(linkTo(methodOn(EstanteController.class).findByIdAndLeitorId(dto.id(),dto.leitorId())).withSelfRel());
        model.add(linkTo(methodOn(EstanteController.class).update(dto.id(), null)).withRel("atualizar"));
        model.add(linkTo(methodOn(EstanteController.class).delete(dto.id(), dto.leitorId())).withRel("excluir"));
        model.add(linkTo(methodOn(ItemEstanteController.class).findAllByEstanteId(dto.id())).withRel("itens_da_estante"));

        return model;
    }

    @Override
    public CollectionModel<EntityModel<EstanteResponseDTO>> toCollectionModel(Iterable<? extends EstanteResponseDTO> entities) {
        List<EntityModel<EstanteResponseDTO>> models = ((List<EstanteResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}