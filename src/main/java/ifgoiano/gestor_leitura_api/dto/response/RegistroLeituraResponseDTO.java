package ifgoiano.gestor_leitura_api.dto.response;

import java.util.Date;

public record RegistroLeituraResponseDTO(Long id, Date data, int paginaAtual, String comentarios, String livro, Long itemId) {
    
}
