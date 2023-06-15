
package acme.components;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	ElementType.FIELD, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomDateTimeFormatValidator.class)
public @interface CustomDateTimeFormat {

	String pattern();

	String message() default "Invalid date and time format. The format should be {pattern}.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
