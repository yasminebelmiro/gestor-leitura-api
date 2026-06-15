package ifgoiano.gestor_leitura_api.controller;

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

import ifgoiano.gestor_leitura_api.assembler.LeitorModelAssembler;
import ifgoiano.gestor_leitura_api.dto.request.LeitorRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.LeitorResponseDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.service.LeitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/leitor")
@Validated
@Tag(name = "Leitor", description = "Endpoints para gerenciamento de leitor")
public class LeitorController {

    private final LeitorService leitorService;
    private final LeitorModelAssembler assembler;

    public LeitorController(LeitorService leitorService, LeitorModelAssembler assembler) {
        this.leitorService = leitorService;
        this.assembler = assembler;
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar leitor por ID",
            description = "Retorna os detalhes do leitor com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leitor encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Leitor não encontrado")
            }
    )
    public ResponseEntity<EntityModel<LeitorResponseDTO>> findById(@PathVariable Long id) {
        LeitorResponseDTO leitor = leitorService.findById(id);
        return ResponseEntity.ok(assembler.toModel(leitor));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Criar um novo leitor",
            description = "Cria um novo leitor com os dados fornecidos no corpo da requisição",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Leitor criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            }
    )
    public ResponseEntity<EntityModel<LeitorResponseDTO>> create(@RequestBody LeitorRequestDTO dto) {
        LeitorResponseDTO novo = leitorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novo));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar um leitor existente",
            description = "Atualiza os detalhes de um leitor existente com base no" +
                    " ID fornecido e nos dados do corpo da requisição",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Leitor atualizado com sucesso"
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"
                    ),
                    @ApiResponse(responseCode = "404", description = "Leitor não encontrado"
                    )
            }
    )
    public ResponseEntity<EntityModel<LeitorResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody LeitorRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(leitorService.update(id, dto)));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Deletar um leitor",
            description = "Remove um leitor existente com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Leitor deletado com sucesso"
                    ),
                    @ApiResponse(responseCode = "404", description = "Leitor não encontrado"
                    )
            }
    )

    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leitorService.detele(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/meta/{ano}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar meta anual por ano",
            description = "Retorna a meta anual do leitor para o ano especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Meta anual encontrada com sucesso",
                            content = @Content(schema = @Schema(implementation = MetaAnualResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor ou meta anual não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID ou ano fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<MetaAnualResponseDTO> buscarMetaPorAno(@PathVariable Long id, @PathVariable Integer ano) {
        return ResponseEntity.ok(leitorService.buscarMetaPorAno(id, ano));
    }

    @GetMapping(value = "/{id}/meta/{ano}/verificar", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Verificar se meta anual foi batida",
            description = "Verifica se o leitor atingiu a meta anual para o ano especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verificação realizada com sucesso",
                            content = @Content(schema = @Schema(implementation = Boolean.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor ou meta anual não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID ou ano fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<Boolean> verificarMetaBatida(@PathVariable Long id, @PathVariable int ano) {
        return ResponseEntity.ok(leitorService.verificarMetaBatida(id, ano));
    }

    @GetMapping(value = "/{id}/progresso/{ano}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Calcular progresso geral do leitor",
            description = "Calcula o progresso geral do leitor em relação à meta anual para o ano especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Progresso calculado com sucesso",
                            content = @Content(schema = @Schema(implementation = Double.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor ou meta anual não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID ou ano fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<Double> calcularProgressoGeral(@PathVariable Long id, @PathVariable int ano) {
        return ResponseEntity.ok(leitorService.calcularProgressoGeral(id, ano));
    }

    @GetMapping(value = "/{id}/livros-lidos/{ano}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Contar livros lidos no ano",
            description = "Retorna a quantidade de livros lidos pelo leitor no ano especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contagem realizada com sucesso",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Leitor não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID ou ano fornecido é inválido"
                    )
            }
    )
    public ResponseEntity<Long> contarLivrosLidosNoAno(@PathVariable Long id, @PathVariable int ano) {
        return ResponseEntity.ok(leitorService.contarLivrosLidosNoAno(id, ano));
    }


}
