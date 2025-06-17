package um.edu.uy.exceptions;

public class ElementoYaExistenteException extends Exception {
    public ElementoYaExistenteException() {
        super("Elemento ya existente en la tabla");
    }
}
