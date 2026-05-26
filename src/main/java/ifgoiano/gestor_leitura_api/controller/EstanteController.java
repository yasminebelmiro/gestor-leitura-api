package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.EstanteResquestDTO;
import ifgoiano.gestor_leitura_api.dto.response.EstanteResponseDTO;
import ifgoiano.gestor_leitura_api.service.EstanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estante")
@Validated
@Tag(name = "Estante", description = "Endpoints para gerenciamento de estantes")
public class EstanteController {

    private EstanteService estanteService;

    public EstanteController(EstanteService estanteService) {
        this.estanteService = estanteService;
    }

    @GetMapping(value = "/{id}/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstanteResponseDTO> findByIdAndLeitorId(@PathVariable Long id, Long leitorId) {
        return ResponseEntity.ok(estanteService.findByIdAndLeitorId(id, leitorId));
    }

    @GetMapping(value = "/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EstanteResponseDTO>> findAllByLeitorId(@PathVariable Long leitorId) {
        return ResponseEntity.ok(estanteService.findAllByLeitorId(leitorId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstanteResponseDTO> create(@RequestBody EstanteResquestDTO dto){
        EstanteResponseDTO novo = estanteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstanteResponseDTO> update(@RequestBody EstanteResquestDTO dto,@PathVariable Long id){
        return ResponseEntity.ok(estanteService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}/leitor/{leitorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Long leitorId){
        estanteService.delete(id, leitorId);
        return ResponseEntity.noContent().build();
    }
}
