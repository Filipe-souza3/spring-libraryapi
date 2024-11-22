package cursoJPA.libraryapi.exception;

public class NaoPermitidoException extends RuntimeException {

    public NaoPermitidoException(String message){
        super(message);
    }

}
