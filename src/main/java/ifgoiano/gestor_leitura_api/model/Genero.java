package ifgoiano.gestor_leitura_api.model;

public enum Genero {
    FICCAO("Ficção"),
    NAO_FICCAO("Não Ficção"),
    FANTASIA("Fantasia"),
    ROMANCE("Romance"),
    MISTERIO("Mistério"),
    AVENTURA("Aventura"),
    HISTORICO("Histórico"),
    CIENCIA_FICCAO("Ciência-Ficção"),
    BIOGRAFIA("Biografia"),
    AUTOAJUDA("Autoajuda");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }
}
