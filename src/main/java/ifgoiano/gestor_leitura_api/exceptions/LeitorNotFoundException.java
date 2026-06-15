package ifgoiano.gestor_leitura_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LeitorNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public LeitorNotFoundException(Long id) {
         super(String.format("Leitor com ID %d não foi encontrado no sistema.", id));
    }

     public LeitorNotFoundException(String mesage) {
         super(mesage);
    }

}
