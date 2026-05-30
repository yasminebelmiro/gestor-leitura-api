package ifgoiano.gestor_leitura_api.controller;

import java.util.List;

import ifgoiano.gestor_leitura_api.model.Livro;
import ifgoiano.gestor_leitura_api.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import ifgoiano.gestor_leitura_api.service.GoogleBooksIntegrationService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/livros")
@Validated
@Tag(name = "Livro", description = "Endpoints para gerenciamento de livros")
public class LivroController {

    private GoogleBooksIntegrationService googleBooksService;
    private final LivroService livroService;

    public LivroController(GoogleBooksIntegrationService googleBooksService, LivroService livroService) {
        this.livroService = livroService;
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar livros",
            description = "Busca livros usando a API do Google Books com base na query fornecida",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Busca realizada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Query de busca inválida"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<List<LivroResponseDTO>> buscar(@RequestParam String q) {
        List<LivroResponseDTO> result = googleBooksService.buscarLivros(q);
        return ResponseEntity.ok(result);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar livro",
            description = "Adiciona um novo livro à coleção com base nos dados fornecidos",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Livro criado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados de entrada inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<Livro> create(@RequestBody LivroResponseDTO dto) {
        Livro novo = livroService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @DeleteMapping(value = "/{googleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deletar livro",
            description = "Remove um livro da coleção com base no Google ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Livro deletado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Livro não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Google ID fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<Void> delete(@PathVariable String googleId) {
        livroService.delete(googleId);
        return ResponseEntity.noContent().build();
    }
}
