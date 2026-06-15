package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.LivroController;
import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LivroModelAssembler implements RepresentationModelAssembler<LivroResponseDTO, EntityModel<LivroResponseDTO>> {

    @Override
    public EntityModel<LivroResponseDTO> toModel(LivroResponseDTO dto) {
        EntityModel<LivroResponseDTO> model = EntityModel.of(dto);
        String googleId = dto.googleId();


        model.add(linkTo(methodOn(LivroController.class).calcularMediaAvaliacoes(googleId)).withRel("media_avaliacoes"));
        model.add(linkTo(methodOn(LivroController.class).exibirFichaTecnicaCompleta(googleId)).withRel("ficha_tecnica_completa"));
        model.add(linkTo(methodOn(LivroController.class).delete(googleId)).withRel("excluir"));

        return model;
    }

    @Override
    public CollectionModel<EntityModel<LivroResponseDTO>> toCollectionModel(Iterable<? extends LivroResponseDTO> entities) {
        List<EntityModel<LivroResponseDTO>> models = ((List<LivroResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}