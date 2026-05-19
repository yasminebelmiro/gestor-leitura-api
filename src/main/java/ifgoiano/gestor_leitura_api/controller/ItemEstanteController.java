package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.ItemEstanteRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ItemEstanteResponseDTO;
import ifgoiano.gestor_leitura_api.service.ItemEstanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-estante")
@Validated
@Tag(name = "ItemEstante", description = "Endpoints para gerenciamento de itens da estante")
public class ItemEstanteController {

    private final ItemEstanteService itemEstanteService;

    public ItemEstanteController(ItemEstanteService itemEstanteService){
        this.itemEstanteService = itemEstanteService;
    }

    @GetMapping(value = "/itemEstante/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemEstanteResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(itemEstanteService.findById(id));
    }
    @GetMapping(value = "/estante/{estanteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemEstanteResponseDTO>> findAllByEstanteId(@PathVariable Long estanteId){
        return ResponseEntity.ok(itemEstanteService.findAllByEstanteId(estanteId));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemEstanteResponseDTO> update(@RequestBody ItemEstanteRequestDTO dto){
        return ResponseEntity.ok(itemEstanteService.upadate(dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        itemEstanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
