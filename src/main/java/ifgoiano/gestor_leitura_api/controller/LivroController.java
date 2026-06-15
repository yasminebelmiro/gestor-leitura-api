package ifgoiano.gestor_leitura_api.controller;

import java.util.List;

import ifgoiano.gestor_leitura_api.assembler.LivroModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;
import ifgoiano.gestor_leitura_api.service.GoogleBooksIntegrationService;
import ifgoiano.gestor_leitura_api.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/livros")
@Validated
@Tag(name = "Livro", description = "Endpoints para gerenciamento de livros")
public class LivroController {

    private GoogleBooksIntegrationService googleBooksService;
    private final LivroService livroService;
    private final LivroModelAssembler assembler;

    public LivroController(GoogleBooksIntegrationService googleBooksService, LivroService livroService, LivroModelAssembler assembler) {
        this.livroService = livroService;
        this.googleBooksService = googleBooksService;
        this.assembler = assembler;
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar livros",
            description = "Busca livros usando a API do Google Books com base na query fornecida",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Query de busca inválida"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    public ResponseEntity<CollectionModel<EntityModel<LivroResponseDTO>>> buscar(@RequestParam String q) {
        List<LivroResponseDTO> result = googleBooksService.buscarLivros(q);
        return ResponseEntity.ok(assembler.toCollectionModel(result));
    }

    @DeleteMapping(value = "/{googleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deletar livro",
            description = "Remove um livro da coleção com base no Google ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Google ID fornecido é inválido")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable String googleId) {
        livroService.delete(googleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{googleId}/media-avaliacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Calcular média de avaliações",
            description = "Calcula a média das avaliações para um livro específico com base no Google ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Média calculada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Google ID fornecido é inválido")
            }
    )
    public ResponseEntity<Double> calcularMediaAvaliacoes(@PathVariable String googleId) {
        Double media = livroService.calcularMediaAvaliacoes(googleId);
        return ResponseEntity.ok(media);
    }

    @GetMapping(value = "/{googleId}/ficha-tecnica-completa", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Exibir ficha técnica completa",
            description = "Exibe a ficha técnica completa de um livro específico com base no Google ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ficha técnica exibida com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Google ID fornecido é inválido"),
            }
    )
    public ResponseEntity<String> exibirFichaTecnicaCompleta(@PathVariable String googleId) {
        String fichaTecnica = livroService.exibirFichaTecnicaCompleta(googleId);
        return ResponseEntity.ok(fichaTecnica);
    }
}