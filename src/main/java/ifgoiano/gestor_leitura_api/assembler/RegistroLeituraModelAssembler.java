package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.RegistroLeituraController;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RegistroLeituraModelAssembler implements RepresentationModelAssembler<RegistroLeituraResponseDTO, EntityModel<RegistroLeituraResponseDTO>> {

    @Override
    public EntityModel<RegistroLeituraResponseDTO> toModel(RegistroLeituraResponseDTO dto) {
        EntityModel<RegistroLeituraResponseDTO> model = EntityModel.of(dto);
        Long id = dto.id();
        Long itemId = dto.itemId();

        // Links CRUD essenciais do recurso
        model.add(linkTo(methodOn(RegistroLeituraController.class).findById(id)).withSelfRel());
        model.add(linkTo(methodOn(RegistroLeituraController.class).update(id, null)).withRel("atualizar"));
        model.add(linkTo(methodOn(RegistroLeituraController.class).delete(id)).withRel("excluir"));

        // Links contextuais e de transição de estado
        if (itemId != null) {
            model.add(linkTo(methodOn(RegistroLeituraController.class).listAllOrdened(itemId)).withRel("registros_do_item"));
        }

        return model;
    }

    @Override
    public CollectionModel<EntityModel<RegistroLeituraResponseDTO>> toCollectionModel(Iterable<? extends RegistroLeituraResponseDTO> entities) {
        List<EntityModel<RegistroLeituraResponseDTO>> models = ((List<RegistroLeituraResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}