
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peep extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@PastOrPresent
	@NotNull(message = "{validation.peep.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiation;

	@NotBlank(message = "{validation.peep.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.peep.notNull}")
	@Length(max = 75)
	protected String			nick;

	@NotBlank(message = "{validation.peep.notNull}")
	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@URL
	protected String			link;

}
