package ifgoiano.gestor_leitura_api.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ifgoiano.gestor_leitura_api.dto.request.GoogleBookItem;
import ifgoiano.gestor_leitura_api.dto.request.IndustryIdentifier;
import ifgoiano.gestor_leitura_api.dto.response.GoogleBooksResponse;
import ifgoiano.gestor_leitura_api.dto.response.LivroResponseDTO;

@Service
public class GoogleBooksIntegrationService {

    @Value("${google.books.api.url}")
    private String apiUrl;

    @Value("${google.books.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(value = {RuntimeException.class,
        RestClientException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000) // Espera 2 segundos antes de
    // tentar de novo
    )
    public List<LivroResponseDTO> buscarLivros(String termoBusca) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", termoBusca)
                .queryParam("key", apiKey)
                .queryParam("langRestrict", "pt")
                .queryParam("hl", "pt-BR")
                .toUriString();

        try {
            GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);
            if (response == null || response.items() == null) {
                return Collections.emptyList();
            }

            return response.items().stream().map(item -> {
                String capa = (item.volumeInfo().imageLinks() != null)
                        ? item.volumeInfo().imageLinks().thumbnail()
                        : "url_para_imagem_sem_capa";
                String fallbackIsbn = "NA-" + item.id();

                // Se for maior que 20 caracteres (o que é raro), cortamos para não dar erro de limite no banco
                if (fallbackIsbn.length() > 20) {
                    fallbackIsbn = fallbackIsbn.substring(0, 20);
                }

                String isbnExtraido = fallbackIsbn;

                if (item.volumeInfo().industryIdentifiers() != null) {
                    final String fallbackFinal = fallbackIsbn; // Variável auxiliar para o lambda

                    isbnExtraido = item.volumeInfo().industryIdentifiers().stream()
                            .filter(id -> "ISBN_13".equals(id.type()))
                            .map(IndustryIdentifier::identifier)
                            .findFirst()
                            .orElseGet(() -> item.volumeInfo().industryIdentifiers().stream()
                            .filter(id -> "ISBN_10".equals(id.type()))
                            .map(IndustryIdentifier::identifier)
                            .findFirst()
                            .orElse(fallbackFinal)); // Usa o fallback único se não achar
                }

                return new LivroResponseDTO(
                        item.id(),
                        item.volumeInfo().title(),
                        item.volumeInfo().authors() != null ? item.volumeInfo().authors()
                        : Collections.singletonList("Autor Desconhecido"),
                        item.volumeInfo().description(),
                        item.volumeInfo().publisher() != null ? item.volumeInfo().publisher() : "Editora Desconhecida",
                        item.volumeInfo().publishedDate(),
                        item.volumeInfo().pageCount(),
                        item.volumeInfo().categories(),
                        capa,
                        isbnExtraido);
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao comunicar com a API do Google Books", e);

        }
    }

    public GoogleBookItem buscarDetalhesCompletos(String googleId) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .pathSegment(googleId)
                .queryParam("key", apiKey)
                .toUriString();

        try {
            return restTemplate.getForObject(url, GoogleBookItem.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar detalhes do livro no Google: " + googleId, e);
        }
    }
}
