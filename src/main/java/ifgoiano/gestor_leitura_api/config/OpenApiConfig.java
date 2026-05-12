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
                .description("API para gerenciamento de leitores, livros e suas leituras. Permite criar, atualizar e" +
                        " deletar leitores, buscar livros via " +
                        "integração com Google Books e gerenciar as leituras dos usuários.");
    }
}
