package ifgoiano.gestor_leitura_api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusLeituraTest {

    @Test
    void deveRetornarDescricoesDosStatus() {
        assertEquals("Lendo", StatusLeitura.LENDO.getDescricao());
        assertEquals("Quero Ler", StatusLeitura.QUERO_LER.getDescricao());
        assertEquals("Lido", StatusLeitura.LIDO.getDescricao());
        assertEquals("Abandonado", StatusLeitura.ABANDONADO.getDescricao());
    }
}

