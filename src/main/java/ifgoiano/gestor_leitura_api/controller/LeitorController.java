package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.LeitorRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import ifgoiano.gestor_leitura_api.service.LeitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leitor")
@Validated
@Tag(name = "Leitor", description = "Endpoints para gerenciamento de leitor")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeitorResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(leitorService.findById(id));
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar leitor por email",
            description = "Retorna os detalhes do leitor com base no email fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leitor encontrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor não encontrado"
                    )
            }
    )
    public ResponseEntity<LeitorResponseDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(leitorService.findByEmail(email));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar um novo leitor",
            description = "Cria um novo leitor com os dados fornecidos no corpo da requisição",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Leitor criado com sucesso",
                            content = @Content(schema = @Schema(implementation = LeitorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados de entrada inválidos",
                            content = @Content(schema = @Schema(implementation = LeitorResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<LeitorResponseDTO> create(@RequestBody LeitorRequestDTO dto) {
        LeitorResponseDTO novo = leitorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
     @Operation(
            summary = "Atualizar um leitor existente",
            description = "Atualiza os detalhes de um leitor existente com base no" +
                    " ID fornecido e nos dados do corpo da requisição",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leitor atualizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dados de entrada inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor não encontrado"
                    )
            }
    )
    public ResponseEntity<LeitorResponseDTO> update(
            @PathVariable Long id,
            @RequestBody LeitorRequestDTO dto) {
        return ResponseEntity.ok(leitorService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
     @Operation(
            summary = "Deletar um leitor",
            description = "Remove um leitor existente com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Leitor deletado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor não encontrado"
                    )
            }
    )

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leitorService.detele(id);
        return ResponseEntity.noContent().build();
    }
}
