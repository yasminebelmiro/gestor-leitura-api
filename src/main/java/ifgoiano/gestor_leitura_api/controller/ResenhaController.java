package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.service.ResenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
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

    public ResenhaController(ResenhaService resenhaService) {
        this.resenhaService = resenhaService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obter resenha por ID",
            description = "Retorna os detalhes de uma resenha específica com base no ID fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenha encontrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resenha não encontrada"
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
    public ResponseEntity<ResenhaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(resenhaService.fingById(id));
    }

    @GetMapping(value = "/{livroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Listar resenhas por livro",
            description = "Retorna uma lista de resenhas associadas a um livro específico com base no ID do livro fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenhas encontradas com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Livro não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do livro fornecido é inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<List<ResenhaResponseDTO>> listByLivro(@PathVariable Long livroId) {
        return ResponseEntity.ok(resenhaService.listByLivro(livroId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar uma nova resenha",
            description = "Permite criar uma nova resenha para um livro específico. O corpo da requisição deve conter os detalhes da resenha, incluindo o ID do livro associado.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resenha criada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados fornecidos são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Livro não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<ResenhaResponseDTO> create(ResenhaRequestDTO dto) {
        ResenhaResponseDTO novo = resenhaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar resenha existente",
            description = "Permite atualizar os detalhes de uma resenha existente com base no ID fornecido. O corpo da requisição deve conter os novos detalhes da resenha.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resenha atualizada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados fornecidos são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Resenha não encontrada")
            }
    )
    public ResponseEntity<ResenhaResponseDTO> update(@PathVariable Long id, ResenhaRequestDTO dto) {
        return ResponseEntity.ok(resenhaService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Excluir resenha",
            description = "Permite excluir uma resenha existente com base no ID fornecido.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Resenha excluída com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resenha não encontrada"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenhaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}