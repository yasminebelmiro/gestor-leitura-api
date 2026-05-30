package ifgoiano.gestor_leitura_api.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo());
    }
    private Info apiInfo() {
        return new Info()
                .title("Gestor de Leitura API")
                .version("v1")
                .description("API para gerenciamento literário que possibilita o controle de livros, estantes, metas e resenhas. " +
                        "Oferece operações completas de CRUD para estantes, gerenciamento do progresso de leitura e " +
                        "busca avançada de títulos integrada à API do Google Books.");
    }
}
