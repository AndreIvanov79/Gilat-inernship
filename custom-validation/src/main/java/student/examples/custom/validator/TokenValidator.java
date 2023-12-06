package student.examples.custom.validator;

import java.util.Base64;
import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TokenValidator implements  ConstraintValidator<ValidToken, String>{
	private String message;

    @Override
    public void initialize(ValidToken constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(value);
            UUID uuid = UUID.nameUUIDFromBytes(decodedBytes);
            return uuid != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
