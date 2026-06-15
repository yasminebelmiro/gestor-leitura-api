package ifgoiano.gestor_leitura_api.assembler;

import ifgoiano.gestor_leitura_api.controller.ItemEstanteController;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemEstanteModelAssembler implements RepresentationModelAssembler<ItemEstanteResponseDTO, EntityModel<ItemEstanteResponseDTO>> {

    @Override
    public EntityModel<ItemEstanteResponseDTO> toModel(ItemEstanteResponseDTO dto) {
        EntityModel<ItemEstanteResponseDTO> model = EntityModel.of(dto);
        Long id = dto.id();

        model.add(linkTo(methodOn(ItemEstanteController.class).findById(id)).withSelfRel());
        model.add(linkTo(methodOn(ItemEstanteController.class).update(null, id)).withRel("atualizar"));
        model.add(linkTo(methodOn(ItemEstanteController.class).delete(id)).withRel("excluir"));

        model.add(linkTo(methodOn(ItemEstanteController.class).marcarComoLendo(id)).withRel("marcar_lendo"));
        model.add(linkTo(methodOn(ItemEstanteController.class).marcarComoLido(id)).withRel("marcar_lido"));
        model.add(linkTo(methodOn(ItemEstanteController.class).marcarComoAbandonado(id)).withRel("marcar_abandonado"));
        model.add(linkTo(methodOn(ItemEstanteController.class).marcarComoQueroLer(id)).withRel("marcar_quero_ler"));

        model.add(linkTo(methodOn(ItemEstanteController.class).buscarPaginaAtual(id)).withRel("pagina_atual"));
        model.add(linkTo(methodOn(ItemEstanteController.class).calcularDiasDeLeitura(id)).withRel("dias_de_leitura"));

        // Se você tiver o ID do leitor no DTO, poderia também expor condicionalmente o link de mover:
        // model.add(linkTo(methodOn(ItemEstanteController.class).moverParaOutraEstante(id, dto.getLeitorId(), null)).withRel("mover"));

        return model;
    }

    @Override
    public CollectionModel<EntityModel<ItemEstanteResponseDTO>> toCollectionModel(Iterable<? extends ItemEstanteResponseDTO> entities) {
        List<EntityModel<ItemEstanteResponseDTO>> models = ((List<ItemEstanteResponseDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models);
    }
}