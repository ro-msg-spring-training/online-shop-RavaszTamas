package ro.msg.learning.shop.exceptions;

public class ConverstionException extends RuntimeException {
  public ConverstionException(String message) {
    super(message);
  }

  public ConverstionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConverstionException(Throwable cause) {
    super(cause);
  }
}
