package ifgoiano.gestor_leitura_api.exceptions;

public class RegistroLeitutraNotfoundException extends RuntimeException {
      private static final long serialVersionUID = 1L;

    public RegistroLeitutraNotfoundException(Long id) {
         super(String.format("Registro com ID %d não foi encontrado no sistema.", id));
    }

     public RegistroLeitutraNotfoundException(String mesage) {
         super(mesage);
    }
}
