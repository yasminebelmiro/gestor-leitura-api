package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.MetaAtualController;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MetaAtualModelAssembler implements RepresentationModelAssembler<MetaAnualResponseDTO, EntityModel<MetaAnualResponseDTO>> {

    @Override
    public EntityModel<MetaAnualResponseDTO> toModel(MetaAnualResponseDTO dto) {
        EntityModel<MetaAnualResponseDTO> model = EntityModel.of(dto);
        Long id = dto.id();
        int ano = dto.ano();

        // Links CRUD base do recurso de Meta
        model.add(linkTo(methodOn(MetaAtualController.class).findById(id)).withSelfRel());
        model.add(linkTo(methodOn(MetaAtualController.class).update(id, null)).withRel("atualizar"));
        model.add(linkTo(methodOn(MetaAtualController.class).delete(id)).withRel("excluir"));

        // Links de operações dinâmicas associadas ao ano da meta
        model.add(linkTo(methodOn(MetaAtualController.class).verificaSeMetaFoiBatida(ano)).withRel("verificar_meta_batida"));
        model.add(linkTo(methodOn(MetaAtualController.class).percentualConclusao(ano)).withRel("percentual_conclusao"));
        model.add(linkTo(methodOn(MetaAtualController.class).atualizarProgresso(ano, 0)).withRel("atualizar_progresso"));

        return model;
    }

    @Override
    public CollectionModel<EntityModel<MetaAnualResponseDTO>> toCollectionModel(Iterable<? extends MetaAnualResponseDTO> entities) {
        List<EntityModel<MetaAnualResponseDTO>> models = ((List<MetaAnualResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}