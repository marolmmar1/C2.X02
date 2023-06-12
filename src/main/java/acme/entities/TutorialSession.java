
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
public class TutorialSession extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.tutorialSession.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.tutorialSession.notNull}")
	@Length(max = 75)
	protected String			abstracts;

	@NotNull(message = "{validation.tutorialSession.notNull}")
	protected Nature			nature;

	@NotNull(message = "{validation.tutorialSession.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				inicialPeriod;

	@NotNull(message = "{validation.tutorialSession.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalPeriod;

	@URL
	protected String			link;

	@NotNull(message = "{validation.tutorialSession.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorial;

}
