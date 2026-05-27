package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.ResenhaRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.ResenhaResponseDTO;
import ifgoiano.gestor_leitura_api.service.ResenhaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenha")
@Validated
@Tag(name = "Resenha", description = "Endpoints para gerenciar as resenhas dos livros")
public class ResenhaController {

    private ResenhaService resenhaService;

    public ResenhaController(ResenhaService resenhaService) {
        this.resenhaService = resenhaService;
    }

    @GetMapping(value = "{/id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResenhaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(resenhaService.fingById(id));
    }

    @GetMapping(value = "{/id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResenhaResponseDTO>> listByLivro(@PathVariable Long livroId) {
        return ResponseEntity.ok(resenhaService.listByLivro(livroId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResenhaResponseDTO> create(ResenhaRequestDTO dto) {
        ResenhaResponseDTO novo = resenhaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    public ResponseEntity<ResenhaResponseDTO> update(@PathVariable Long id, ResenhaRequestDTO dto) {
        return ResponseEntity.ok(resenhaService.update(id, dto));
    }

    @DeleteMapping(value = "{/id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenhaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}