
package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Enrolment extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}", message = "{validation.enrolment.code}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			motivation;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Temporal(TemporalType.TIME)
	protected Date				workTime;

}
