package ifgoiano.gestor_leitura_api.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ifgoiano.gestor_leitura_api.exceptions.EstanteNotFoundException;
import ifgoiano.gestor_leitura_api.exceptions.ExceptionResponse;
import ifgoiano.gestor_leitura_api.exceptions.ItemEstanteNotFoundException;
import ifgoiano.gestor_leitura_api.exceptions.LeitorNotFoundException;
import ifgoiano.gestor_leitura_api.exceptions.LivroNotFoundException;
import ifgoiano.gestor_leitura_api.exceptions.MetaAnualNotFoundException;
import ifgoiano.gestor_leitura_api.exceptions.RegistroLeitutraNotfoundException;
import ifgoiano.gestor_leitura_api.exceptions.ResenhaNotFoundException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LeitorNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleLeitorException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstanteNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleEstanteException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemEstanteNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleItemEstanteException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistroLeitutraNotfoundException.class)
    public final ResponseEntity<ExceptionResponse> handleRegistroException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResenhaNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResenhaException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MetaAnualNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleMetaException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LivroNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleLivroException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
