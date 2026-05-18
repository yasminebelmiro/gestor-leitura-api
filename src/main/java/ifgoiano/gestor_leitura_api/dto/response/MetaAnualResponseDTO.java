package ifgoiano.gestor_leitura_api.dto.response;

public record MetaAnualResponseDTO(long id, int ano, int quantidadeAlvo, int quantidadeAlcancada, Long leitorId) {
}