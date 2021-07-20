package ro.msg.learning.shop.service.validators;

public interface Validator<T> {
    boolean validate(T itemToValidate);
}
