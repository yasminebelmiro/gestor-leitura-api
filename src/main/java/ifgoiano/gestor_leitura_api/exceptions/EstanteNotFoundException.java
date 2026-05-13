package ifgoiano.gestor_leitura_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstanteNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EstanteNotFoundException(Long id) {
         super(String.format("Estante com ID %d não foi encontrado no sistema.", id));
    }

     public EstanteNotFoundException(String mesage) {
         super(mesage);
    }

}
