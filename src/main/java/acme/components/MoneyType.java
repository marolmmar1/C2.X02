
package acme.components;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	FIELD, PARAMETER
})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MoneyTypeValidator.class)
public @interface MoneyType {

	String message() default "{validation.moneyType.notValid}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
