
package acme.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomDateTimeFormatValidator implements ConstraintValidator<CustomDateTimeFormat, Date> {

	private String pattern;


	@Override
	public void initialize(final CustomDateTimeFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(final Date value, final ConstraintValidatorContext context) {
		if (value == null)
			return true;

		final DateFormat dateFormat = new SimpleDateFormat(this.pattern);
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(dateFormat.format(value));
			return true;
		} catch (final ParseException e) {
			return false;
		}
	}
}
