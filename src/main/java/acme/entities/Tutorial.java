
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.tutorial.notNull}")
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.tutorial.pattern}")
	protected String			code;

	@NotBlank(message = "{validation.tutorial.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.tutorial.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@NotBlank(message = "{validation.tutorial.notNull}")
	@Length(max = 100)
	protected String			goals;

	@NotNull(message = "{validation.tutorial.notNull}")
	protected boolean			draftMode;

	@NotNull(message = "{validation.tutorial.notNull}")
	@PositiveOrZero(message = "{validation.tutorial.greaterThanZero}")
	protected double			estimatedTime;

	// Relationships ----------------------------------------------------------
	@NotNull(message = "{validation.tutorial.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Assistant			assistant;

	@NotNull(message = "{validation.tutorial.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

}
