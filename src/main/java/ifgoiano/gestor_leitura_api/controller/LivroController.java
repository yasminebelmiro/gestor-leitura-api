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
            summary = "Buscar livros na API do Google Books",
            description = "Realiza uma busca de livros na API do Google Books com base no termo fornecido como parâmetro" +
                    " 'q'. Retorna uma lista de livros correspondentes à busca.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Busca realizada com sucesso, retornando a lista de livros encontrados"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parâmetro de busca 'q' ausente ou inválido"
                    )
            }
    )
    public ResponseEntity<List<LivroResponseDTO>> buscar(@RequestParam String q) {
        List<LivroResponseDTO> result = googleBooksService.buscarLivros(q);
        return ResponseEntity.ok(result);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "",
            description = "",
            responses = {}
    )
    public ResponseEntity<Livro> create(@RequestBody LivroResponseDTO dto) {
        Livro novo = livroService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @DeleteMapping(value = "/{googleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deletar livro",
            description = "Remove um livro existente com base no googleId fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "livro deletado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "livro não encontrado"
                    )
            }
    )
    public ResponseEntity<Void> delete(@PathVariable String googleId) {
        livroService.delete(googleId);
        return ResponseEntity.noContent().build();
    }
}
