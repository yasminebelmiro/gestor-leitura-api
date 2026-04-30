package ifgoiano.gestor_leitura_api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import ifgoiano.gestor_leitura_api.service.GoogleBooksIntegrationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ifgoiano.gestor_leitura_api.dto.LivroDTO;


@Controller
@RequestMapping("/api/livros")
public class LivroController {

    private GoogleBooksIntegrationService googleBooksService;

    public LivroController(GoogleBooksIntegrationService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LivroDTO>> buscar(@RequestParam String q) {
       List<LivroDTO> result = googleBooksService.buscarLivros(q);
       return ResponseEntity.ok(result);
    }
    

}
