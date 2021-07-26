package ro.msg.learning.shop.exceptions;

public class OrderException extends RuntimeException {
  public OrderException(String message) {
    super(message);
  }

  public OrderException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderException(Throwable cause) {
    super(cause);
  }
}
