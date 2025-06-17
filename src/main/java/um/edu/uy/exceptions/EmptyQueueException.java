package um.edu.uy.exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("La cola está vacía");
    }
}