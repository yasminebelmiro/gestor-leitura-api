package ifgoiano.gestor_leitura_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResenhaNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResenhaNotFoundException(Long id) {
         super(String.format("Resenha com ID %d não foi encontrado no sistema.", id));
    }

     public ResenhaNotFoundException(String mesage) {
         super(mesage);
    }

}
