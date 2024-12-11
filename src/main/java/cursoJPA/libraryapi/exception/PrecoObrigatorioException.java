package cursoJPA.libraryapi.exception;

public class PrecoObrigatorioException extends RuntimeException {

    private String campo;

    public PrecoObrigatorioException(String msg, String campo) {
        super(msg);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    

}
