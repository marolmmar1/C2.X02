
package acme.components;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyTypeValidator implements ConstraintValidator<MoneyType, String> {

	private static final Set<String> ALLOWED_MONEY_TYPES = new HashSet<>(Arrays.asList("EUR", "GBP", "USD", "YEN", "AUD", "CHF", "DKK", "NOK", "NZD", "XAF", "XCD", "XOF", "ZAR"));


	@Override
	public boolean isValid(final String value, final ConstraintValidatorContext context) {
		if (value == null)
			return true; // Null values are considered valid, you can adjust this as per your requirements
		final String[] moneyTypes = value.split(",");
		for (final String moneyType : moneyTypes)
			if (!MoneyTypeValidator.ALLOWED_MONEY_TYPES.contains(moneyType.trim().toUpperCase()))
				return false;
		return true;
	}
}
