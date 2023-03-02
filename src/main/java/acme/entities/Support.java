
package acme.entities;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;

public class Support extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorial;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Tutorial			tutorialSessions;

}
