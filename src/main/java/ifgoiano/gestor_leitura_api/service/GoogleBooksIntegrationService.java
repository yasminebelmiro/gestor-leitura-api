package ifgoiano.gestor_leitura_api.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ifgoiano.gestor_leitura_api.dto.GoogleBooksResponse;
import ifgoiano.gestor_leitura_api.dto.LivroDTO;

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

    public List<LivroDTO> buscarLivros(String termoBusca) {
        String url = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("q", termoBusca)
                .queryParam("langRestrict", "pt-BR")
                .queryParam("hl", "pt-BR")
                .queryParam("key", apiKey)
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

                return new LivroDTO(
                        item.id(),
                        item.volumeInfo().title(),
                        item.volumeInfo().authors() != null ? item.volumeInfo().authors()
                                : Collections.singletonList("Autor Desconhecido"),
                        item.volumeInfo().description(),
                        item.volumeInfo().publisher() != null ?  item.volumeInfo().publisher() : "Editora Desconhecida",
                        item.volumeInfo().publishedDate(),
                        item.volumeInfo().pageCount(),
                        item.volumeInfo().categories(),
                        capa
                );
            }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Falha ao comunicar com a API do Google Books", e);

        }
    }
}
