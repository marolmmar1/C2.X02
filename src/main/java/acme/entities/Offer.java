
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer extends AbstractEntity {

	// Serialisation identifier 
	protected static final long	serialVersionUID	= 1L;

	// Attributes
	@NotNull(message = "{validation.offer.notNull}")
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiation;

	@NotBlank(message = "{validation.offer.notNull}")
	@Length(max = 75)
	protected String			heading;

	@NotBlank(message = "{validation.offer.notNull}")
	@Length(max = 100)
	protected String			summary;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "{validation.offer.notNull}")
	protected Date				inicialPeriod;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "{validation.offer.notNull}")
	protected Date				finalPeriod;

	@NotNull(message = "{validation.offer.notNull}")
	protected Money				price;

	@URL
	protected String			link;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
