package um.edu.uy.exceptions;

public class FueraDeRango extends RuntimeException {
  public FueraDeRango(String message) {
    super(message);
  }
}
