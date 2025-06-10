package um.edu.uy.Exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("La cola está vacía");
    }
}