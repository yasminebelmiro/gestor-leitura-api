package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.MetaAnualRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.service.MetaAtualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meta-atual")
@Validated
@Tag(name = "Meta Atual", description = "Endpoints para gerenciar a meta anual atual do leitor")
public class MetaAtualController {

    private MetaAtualService metaAtualService;

    public MetaAtualController(MetaAtualService metaAtualService) {
        this.metaAtualService = metaAtualService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar meta anual atual por ID",
            description = "Retorna os detalhes da meta anual atual com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Meta anual atual encontrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Meta anual atual não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<MetaAnualResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(metaAtualService.findById(id));
    }

    @GetMapping(value = "/leitor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar meta anual atual por ID do leitor",
            description = "Retorna a meta anual atual associada ao leitor com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Meta anual atual listada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor ou meta anual atual não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<List<MetaAnualResponseDTO>> listByLeitor(@PathVariable Long id) {
        return ResponseEntity.ok(metaAtualService.listByLeitor(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar nova meta anual atual",
            description = "Cria uma nova meta anual atual com base nos dados fornecidos",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Meta anual atual criada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados fornecidos são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<MetaAnualResponseDTO> create(MetaAnualRequestDTO dto) {
        MetaAnualResponseDTO novo = metaAtualService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar meta anual atual por ID",
            description = "Atualiza os detalhes da meta anual atual com base no ID fornecido e nos dados fornecidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Meta anual atual atualizada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Meta anual atual não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID ou dados fornecidos são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<MetaAnualResponseDTO> update(@PathVariable Long id, MetaAnualRequestDTO dto) {
        return ResponseEntity.ok(metaAtualService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Excluir meta anual atual por ID",
            description = "Exclui a meta anual atual com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Meta anual atual excluída com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Meta anual atual não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido"
                    ),
                     @ApiResponse(
                             responseCode = "403",
                             description = "Acesso negado para excluir a meta anual atual"
                     ),
                     @ApiResponse(
                             responseCode = "401",
                             description = "Usuário não autenticado para excluir a meta anual atual"
                     ),
                     @ApiResponse(
                             responseCode = "500",
                             description = "Erro interno do servidor"
                     )
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metaAtualService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
