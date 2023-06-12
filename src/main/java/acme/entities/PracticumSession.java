
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.practicumSession.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.practicumSession.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@NotNull(message = "{validation.practicumSession.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				inicialPeriod;

	@NotNull(message = "{validation.practicumSession.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalPeriod;

	@URL
	protected String			link;

	protected boolean			exceptional;

	//Relaciones
	@NotNull(message = "{validation.practicumSession.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Practicum			practicum;

}
