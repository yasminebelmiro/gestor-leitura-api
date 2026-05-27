package ifgoiano.gestor_leitura_api.controller;

import ifgoiano.gestor_leitura_api.dto.request.RegistroLeituraRequestDTO;
import ifgoiano.gestor_leitura_api.dto.response.RegistroLeituraResponseDTO;
import ifgoiano.gestor_leitura_api.service.RegistroLeituraService;
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
        public ResponseEntity<RegistroLeituraResponseDTO> findById(@PathVariable Long id) {
            return ResponseEntity.ok(registroLeituraService.findById(id));
        }

        @GetMapping(value = "/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<RegistroLeituraResponseDTO>> listAllOrdened(@PathVariable Long itemId) {
            return ResponseEntity.ok(registroLeituraService.listAllOrdened(itemId));
        }

        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RegistroLeituraResponseDTO> create(RegistroLeituraRequestDTO dto) {
            RegistroLeituraResponseDTO novo = registroLeituraService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        }

        @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RegistroLeituraResponseDTO> update(@PathVariable Long id, RegistroLeituraRequestDTO dto) {
            return ResponseEntity.ok(registroLeituraService.update(id, dto));
        }

        @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            registroLeituraService.delete(id);
            return ResponseEntity.noContent().build();
        }

}
