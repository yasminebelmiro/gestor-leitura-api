package ifgoiano.gestor_leitura_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MetaAnualNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MetaAnualNotFoundException(Long id) {
         super(String.format("Meta com ID %d não foi encontrado no sistema.", id));
    }

     public MetaAnualNotFoundException(String mesage) {
         super(mesage);
    }

}
