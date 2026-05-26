package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.MetaAnualRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.MetaAnualResponseDTO;
import ifgoiano.gestor_leitura_api.service.MetaAtualService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meta-atual")
@Validated
@Tag(name = "Meta Atual", description = "Endpoints para gerenciar a meta anual atual do leitor")
public class MetaAtualController {

    private MetaAtualService metaAtualService;

    public MetaAtualController(MetaAtualService metaAtualService) {
        this.metaAtualService = metaAtualService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MetaAnualResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(metaAtualService.findById(id));
    }

    @GetMapping(value = "/leitor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MetaAnualResponseDTO>> listByLeitor(@PathVariable Long id) {
        return ResponseEntity.ok(metaAtualService.listByLeitor(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MetaAnualResponseDTO> create(MetaAnualRequestDTO dto) {
        MetaAnualResponseDTO novo = metaAtualService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MetaAnualResponseDTO> update(@PathVariable Long id, MetaAnualRequestDTO dto) {
        return ResponseEntity.ok(metaAtualService.update(id, dto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        metaAtualService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
