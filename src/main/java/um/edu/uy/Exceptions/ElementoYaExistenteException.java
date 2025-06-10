package um.edu.uy.Exceptions;

public class ElementoYaExistenteException extends Exception {
    public ElementoYaExistenteException() {
        super("Elemento ya existente en la tabla");
    }
}
