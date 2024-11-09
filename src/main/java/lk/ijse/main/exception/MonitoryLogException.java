// MonitoryLogNotFoundException.java
package lk.ijse.main.exception;

public class MonitoryLogException extends RuntimeException {
  public MonitoryLogException() {
  }

  public MonitoryLogException(String message) {
    super(message);
  }

  public MonitoryLogException(String message, Throwable cause) {
    super(message, cause);
  }
}