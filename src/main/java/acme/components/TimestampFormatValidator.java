
package acme.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.TemporalType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimestampFormatValidator implements ConstraintValidator<TimestampFormat, Date> {

	private TemporalType temporalType;


	@Override
	public void initialize(final TimestampFormat constraintAnnotation) {
		this.temporalType = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(final Date value, final ConstraintValidatorContext context) {
		if (value == null)
			return true; // Permite valores nulos, si es necesario

		final DateFormat dateFormat = this.getDateFormat();
		dateFormat.setLenient(false); // No permite formatos incorrectos

		try {
			dateFormat.parse(dateFormat.format(value));
			return true;
		} catch (final ParseException e) {
			return false;
		}
	}

	private DateFormat getDateFormat() {
		switch (this.temporalType) {
		case DATE:
			return new SimpleDateFormat("dd/MM/yyyy");
		case TIME:
			return new SimpleDateFormat("HH:mm:ss");
		case TIMESTAMP:
			return new SimpleDateFormat("dd/MM/yyyy HH:mm");
		default:
			throw new IllegalArgumentException("Tipo temporal no compatible: " + this.temporalType);
		}
	}
}
