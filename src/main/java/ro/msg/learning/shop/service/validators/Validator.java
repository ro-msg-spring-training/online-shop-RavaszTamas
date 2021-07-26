package ro.msg.learning.shop.service.validators;

import ro.msg.learning.shop.exceptions.ValidatorException;

public interface Validator<T> {
  void validate(T itemToValidate) throws ValidatorException;
}
