
package acme.components;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.TemporalType;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	ElementType.FIELD, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimestampFormatValidator.class)
public @interface TimestampFormat {

	TemporalType value();

	String message() default "validation.timestampFormatError";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
