package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.assembler.ResenhaModelAssembler;
import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.service.ResenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenha")
@Validated
@Tag(name = "Resenha", description = "Endpoints para gerenciar as resenhas dos livros")
public class ResenhaController {

    private ResenhaService resenhaService;
    private final ResenhaModelAssembler assembler;

    public ResenhaController(ResenhaService resenhaService, ResenhaModelAssembler assembler) {
        this.resenhaService = resenhaService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obter resenha por ID",
            description = "Retorna os detalhes de uma resenha específica com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resenha encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada"),
                    @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<EntityModel<ResenhaResponseDTO>> findById(@PathVariable Long id) {
        ResenhaResponseDTO dto = resenhaService.fingById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping(value = "/livro/{livroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar resenhas por livro",
            description = "Retorna uma lista de resenhas associadas a um livro específico com base no ID do livro fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resenhas encontradas com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
                    @ApiResponse(responseCode = "400", description = "ID do livro fornecido é inválido"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<CollectionModel<EntityModel<ResenhaResponseDTO>>> listByLivro(@PathVariable Long libroId) {
        List<ResenhaResponseDTO> resenhas = resenhaService.listByLivro(libroId);
        return ResponseEntity.ok(assembler.toCollectionModel(resenhas));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar uma nova resenha",
            description = "Permite criar uma nova resenha para um livro específico. O corpo da requisição deve conter os detalhes da resenha, incluindo o ID do livro associado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resenha criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<EntityModel<ResenhaResponseDTO>> create(@RequestBody ResenhaRequestDTO dto) {
        ResenhaResponseDTO novo = resenhaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novo));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar resenha existente",
            description = "Permite atualizar os detalhes de uma resenha existente com base no ID fornecido. O corpo da requisição deve conter os novos detalhes da resenha.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resenha atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos"),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada")
            }
    )
    public ResponseEntity<EntityModel<ResenhaResponseDTO>> update(@PathVariable Long id, @RequestBody ResenhaRequestDTO dto) { // Adicionado @RequestBody
        ResenhaResponseDTO atualizado = resenhaService.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(atualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Excluir resenha",
            description = "Permite excluir uma resenha existente com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resenha excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Resenha não encontrada"),
                    @ApiResponse(responseCode = "400", description = "ID fornecido é inválido")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenhaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}