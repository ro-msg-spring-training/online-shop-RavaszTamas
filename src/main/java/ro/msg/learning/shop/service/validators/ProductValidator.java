package ro.msg.learning.shop.service.validators;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.domain.Product;
import ro.msg.learning.shop.exceptions.ValidatorException;

import java.math.BigDecimal;

@Component
public class ProductValidator implements Validator<Product> {

  public static boolean isNullOrEmpty(String stringToCheck) {
    return stringToCheck == null || stringToCheck.equals("");
  }

  @Override
  public void validate(Product itemToValidate) throws ValidatorException {
    if (isNullOrEmpty(itemToValidate.getName()))
      throw new ValidatorException("Invalid description. It must be non-empty!");
    if (isNullOrEmpty(itemToValidate.getDescription()))
      throw new ValidatorException("Invalid name. It must be non-empty!");
    if (itemToValidate.getPrice().compareTo(BigDecimal.ZERO) < 0)
      throw new ValidatorException("Invalid price. It must be non-negative!");
    if (itemToValidate.getWeight() <= 0)
      throw new ValidatorException("Invalid weight. It must be positive!");
    if (isNullOrEmpty(itemToValidate.getImageUrl()))
      throw new ValidatorException("Invalid image URL. It must be non-empty!");
  }
}
