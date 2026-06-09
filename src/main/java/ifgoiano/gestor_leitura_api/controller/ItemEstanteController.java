package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.service.ItemEstanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/item-estante")
@Validated
@Tag(name = "ItemEstante", description = "Endpoints para gerenciamento de itens da estante")
public class ItemEstanteController {

    private final ItemEstanteService itemEstanteService;

    public ItemEstanteController(ItemEstanteService itemEstanteService) {
        this.itemEstanteService = itemEstanteService;
    }

    @GetMapping(value = "/itemEstante/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar item da estante por ID",
            description = "Retorna os detalhes do item da estante com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item da estante encontrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<ItemEstanteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemEstanteService.findById(id));
    }

    @GetMapping(value = "/estante/{estanteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar itens da estante por ID da estante",
            description = "Retorna os detalhes dos itens da estante com base no ID da estante fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Itens da estante encontrados com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Itens da estante não encontrados"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID da estante fornecido é inválido"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<List<ItemEstanteResponseDTO>> findAllByEstanteId(@PathVariable Long estanteId) {
        return ResponseEntity.ok(itemEstanteService.findAllByEstanteId(estanteId));
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Adicionar item à estante",
            description = "Adiciona um novo item à estante com base nos detalhes fornecidos",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item da estante criado com sucesso"
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
    public ResponseEntity<ItemEstanteResponseDTO> create(@RequestBody ItemEstanteRequestDTO dto) {
        return ResponseEntity.ok(itemEstanteService.create(dto));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Atualizar item da estante",
            description = "Atualiza os detalhes do item da estante com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item da estante atualizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido ou dados de atualização são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<ItemEstanteResponseDTO> update(@RequestBody ItemEstanteRequestDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(itemEstanteService.upadate(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Excluir item da estante",
            description = "Exclui o item da estante com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Item da estante excluído com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
        itemEstanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/mover/{idItem}/leitor/{idLeitor}/nova-estante/{idNovaEstante}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Mover item para outra estante",
            description = "Move um item da estante para outra estante do mesmo leitor",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item movido para outra estante com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante ou nova estante não encontrado"
                    ),
            }
    )
    public ResponseEntity<Void> moverParaOutraEstante(@PathVariable Long idItem, @PathVariable Long idLeitor, @PathVariable Long idNovaEstante) {
        itemEstanteService.moverParaOutraEstante(idItem, idLeitor, idNovaEstante);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/marcar-abandonado/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Marcar item como abandonado",
            description = "Marca um item da estante como abandonado com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item marcado como abandonado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<Void> marcarComoAbandonado(@PathVariable Long id) {
        itemEstanteService.marcarComoAbandonado(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/marcar-lido/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Marcar item como lido",
            description = "Marca um item da estante como lido com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item marcado como lido com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<Void> marcarComoLido(@PathVariable Long id) {
        itemEstanteService.marcarComoLido(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/marcar-quero-ler/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Marcar item como quero ler",
            description = "Marca um item da estante como quero ler com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Item marcado como quero ler com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<Void> marcarComoQueroLer(@PathVariable Long id) {
        itemEstanteService.marcarComoQueroLer(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/marcar-lendo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> marcarComoLendo(@PathVariable Long id) {
        itemEstanteService.marcarComoLendo(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "/adicionar-registro-leitura/{idItemEstante}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Adicionar registro de leitura",
            description = "Adiciona um registro de leitura para um item da estante com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registro de leitura adicionado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID fornecido é inválido ou dados do registro de leitura são inválidos"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno do servidor"
                    )
            }
    )
    public ResponseEntity<RegistroLeituraResponseDTO> adicionarRegistroLeitura(@PathVariable Long idItemEstante, @RequestParam LocalDate data, @RequestParam int paginaAtual, @RequestParam String cometario) {
        RegistroLeituraResponseDTO response = itemEstanteService.adicionarRegistroLeitura(idItemEstante,data, paginaAtual, cometario);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/calcular-dias-leitura/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Calcular dias de leitura",
            description = "Calcula o número de dias que um item da estante está em leitura com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dias de leitura calculados com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<Integer>calcularDiasDeLeitura(@PathVariable Long id) {
        int dias = itemEstanteService.calcularDiasDeLeitura(id);
        return ResponseEntity.ok(dias);
    }
    @GetMapping(value = "/buscar-pagina-atual/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Buscar página atual da leitura",
            description = "Busca a página atual de leitura de um item da estante com base no ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Página atual da leitura buscada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Item da estante não encontrado"
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
    public ResponseEntity<Integer> buscarPaginaAtual(@PathVariable Long id) {
        int paginaAtual = itemEstanteService.buscarPaginaAtual(id);
        return ResponseEntity.ok(paginaAtual);
    }
}
