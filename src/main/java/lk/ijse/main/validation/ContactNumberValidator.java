package lk.ijse.main.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<ValidContactNumber, String> {

    @Override
    public void initialize(ValidContactNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches("\\d{10}");
    }
}