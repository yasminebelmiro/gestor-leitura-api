package ifgoiano.gestor_leitura_api.controller;

import java.util.List;

import ifgoiano.gestor_leitura_api.assembler.EstanteModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifgoiano.gestor_leitura_api.dto.request.EstanteResquestDTO;
import ifgoiano.gestor_leitura_api.dto.response.EstanteResponseDTO;
import ifgoiano.gestor_leitura_api.service.EstanteService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/estante")
@Validated
@Tag(name = "Estante", description = "Endpoints para gerenciamento de estantes")
public class EstanteController {

    private EstanteService estanteService;
    private final EstanteModelAssembler assembler;

    public EstanteController(EstanteService estanteService, EstanteModelAssembler assembler) {
        this.estanteService = estanteService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar estante por ID e ID do leitor",
            description = "Retorna os detalhes da estante com base no ID fornecido e no ID do leitor associado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estante encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Estante ou leitor não encontrado"),
                    @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
                    @ApiResponse(responseCode = "500",description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<EntityModel<EstanteResponseDTO>> findByIdAndLeitorId(@PathVariable Long id, @PathVariable Long leitorId) {
        EstanteResponseDTO dto = estanteService.findByIdAndLeitorId(id, leitorId);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping(value = "/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar todas as estantes por ID do leitor",
            description = "Retorna uma lista de estantes associadas ao ID do leitor fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estantes encontradas com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Leitor não encontrado"),
                    @ApiResponse(responseCode = "400", description = "ID do leitor fornecido é inválido"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<CollectionModel<EntityModel<EstanteResponseDTO>>> findAllByLeitorId(@PathVariable Long leitorId) {
        List<EstanteResponseDTO> estantes = estanteService.findAllByLeitorId(leitorId);
        return ResponseEntity.ok(assembler.toCollectionModel(estantes));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar nova estante",
            description = "Cria uma nova estante com base nos dados fornecidos no corpo da requisição",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Estante criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<EntityModel<EstanteResponseDTO>> create(@RequestBody EstanteResquestDTO dto) {
        EstanteResponseDTO novo = estanteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novo));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar estante",
            description = "Atualiza os detalhes de uma estante existente com base no ID fornecido e nos dados fornecidos no corpo da requisição",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estante atualizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Estante não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<EntityModel<EstanteResponseDTO>> update(@PathVariable Long id, @RequestBody EstanteResquestDTO dto) {
        EstanteResponseDTO atualizado = estanteService.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizado));
    }

    @DeleteMapping(value = "/{id}/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Excluir estante",
            description = "Exclui uma estante existente com base no ID fornecido e no ID do leitor associado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Estante excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Estante ou leitor não encontrado"),
                    @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long leitorId) {
        estanteService.delete(id, leitorId);
        return ResponseEntity.noContent().build();
    }
}
