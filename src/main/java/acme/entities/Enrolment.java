
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Student;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.enrolment.notNull}")
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.enrolment.code}")
	protected String			code;

	@NotBlank(message = "{validation.enrolment.notNull}")
	@Length(max = 75)
	protected String			motivation;

	@NotBlank(message = "{validation.enrolment.notNull}")
	@Length(max = 100)
	protected String			goals;

	//Relaciones

	@NotNull(message = "{validation.enrolment.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Student			student;

	@NotNull(message = "{validation.enrolment.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	protected boolean			draftMode;

	protected String			holderName;

	protected String			lowerNibble;

	@Transient
	@CreditCardNumber(message = "{validation.enrolment.creaditCardError}")
	private String				creditCard;

	@Transient
	private String				cvc;

	@Transient
	private String				expiryDate;

}
