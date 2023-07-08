
package acme.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import acme.components.MoneyType;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank(message = "{validation.systemConfiguration.notNull}")
	@Pattern(regexp = "^[A-Z]{3}$")
	@MoneyType
	protected String			systemCurrency;

	@NotBlank(message = "{validation.systemConfiguration.notNull}")
	@Pattern(regexp = "^[A-Z]{3}(,[A-Z]{3})*$")
	@MoneyType
	protected String			acceptedCurrencies;

}
