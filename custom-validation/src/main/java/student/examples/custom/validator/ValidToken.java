package student.examples.custom.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TokenValidator.class)
public @interface ValidToken {

	 String message() default "Invalid token";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};
}
