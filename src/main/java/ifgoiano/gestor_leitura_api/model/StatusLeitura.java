package ifgoiano.gestor_leitura_api.model;

public enum StatusLeitura {
    LENDO("Lendo"),
    QUERO_LER("Quero Ler"),
    LIDO("Lido"),
    ABANDONADO("Abandonado");

    private final String descricao;

    StatusLeitura(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
