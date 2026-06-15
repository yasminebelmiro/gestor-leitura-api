package ifgoiano.gestor_leitura_api.model;

public enum StatusLeitura {
    QUERO_LER("Quero Ler"),
    LENDO("Lendo"),
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
