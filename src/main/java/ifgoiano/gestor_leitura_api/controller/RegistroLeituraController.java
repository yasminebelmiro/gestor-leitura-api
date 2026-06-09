package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.RegistroLeituraRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.service.RegistroLeituraService;
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
@Validated
@RequestMapping("/api/registro-leitura")
@Tag(name = "Registro de Leitura", description = "Endpoints para gerenciar os registros de leitura dos livros")
public class RegistroLeituraController {

    private RegistroLeituraService registroLeituraService;

    public RegistroLeituraController(RegistroLeituraService registroLeituraService) {
        this.registroLeituraService = registroLeituraService;
    }

        @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Buscar registro de leitura por ID",
                description = "Retorna os detalhes do registro de leitura com base no ID fornecido",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Registro de leitura encontrado com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Registro de leitura não encontrado"
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
        public ResponseEntity<RegistroLeituraResponseDTO> findById(@PathVariable Long id) {
            return ResponseEntity.ok(registroLeituraService.findById(id));
        }

        @GetMapping(value = "/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Listar registros de leitura por item",
                description = "Retorna uma lista de registros de leitura associados a um item específico, ordenados por data de leitura",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Registros de leitura encontrados com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Nenhum registro de leitura encontrado para o item fornecido"
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "ID do item fornecido é inválido"
                        ),
                        @ApiResponse(
                                responseCode = "500",
                                description = "Erro interno do servidor"
                        )
                }
        )
        public ResponseEntity<List<RegistroLeituraResponseDTO>> listAllOrdened(@PathVariable Long itemId) {
            return ResponseEntity.ok(registroLeituraService.listAllOrdened(itemId));
        }

        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Criar um novo registro de leitura",
                description = "Cria um novo registro de leitura para um item específico, associando-o a um leitor e registrando a data de leitura",
                responses = {
                        @ApiResponse(
                                responseCode = "201",
                                description = "Registro de leitura criado com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Dados fornecidos são inválidos"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Leitor ou item não encontrado para os IDs fornecidos"
                        ),
                        @ApiResponse(
                                responseCode = "500",
                                description = "Erro interno do servidor"
                        )
                }
        )
        public ResponseEntity<RegistroLeituraResponseDTO> create(RegistroLeituraRequestDTO dto) {
            RegistroLeituraResponseDTO novo = registroLeituraService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        }

        @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Atualizar um registro de leitura",
                description = "Atualiza os detalhes de um registro de leitura existente com base no ID fornecido e nos dados fornecidos no corpo da requisição",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Registro de leitura atualizado com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Registro de leitura não encontrado para o ID fornecido"
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
        public ResponseEntity<RegistroLeituraResponseDTO> update(@PathVariable Long id, RegistroLeituraRequestDTO dto) {
            return ResponseEntity.ok(registroLeituraService.update(id, dto));
        }

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Excluir um registro de leitura",
                description = "Exclui um registro de leitura existente com base no ID fornecido",
                responses = {
                        @ApiResponse(
                                responseCode = "204",
                                description = "Registro de leitura excluído com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Registro de leitura não encontrado para o ID fornecido"
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
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            registroLeituraService.delete(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping(value = "/{itemId}/{registroId}/progresso", produces = MediaType.APPLICATION_JSON_VALUE)
        @Operation(
                summary = "Atualizar progresso de leitura",
                description = "Atualiza o progresso de leitura de um registro específico, permitindo que o usuário informe a página atual e um comentário sobre o progresso",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Progresso de leitura atualizado com sucesso"
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Registro de leitura ou item não encontrado para os IDs fornecidos"
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
        public ResponseEntity<Void> atualizarProgresso(@PathVariable Long itemId, @PathVariable Long registroId, @RequestParam int pagina, @RequestParam String comentario) {
            registroLeituraService.atualizarProgresso(itemId, registroId, pagina, comentario);
            return ResponseEntity.ok().build();
        }
}
