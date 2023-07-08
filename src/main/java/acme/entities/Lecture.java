
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.lecture.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.lecture.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@Digits(integer = 3, fraction = 2)
	@NotNull(message = "{validation.lecture.notNull}")
	@Positive
	@Max(1000)
	protected Double			estimatedTime;

	@NotBlank(message = "{validation.lecture.notNull}")
	@Length(max = 100)
	protected String			body;

	@NotNull(message = "{validation.lecture.notNull}")
	protected Nature			nature;

	@URL
	protected String			link;

	protected boolean			draftMode;

	// Relationships ----------------------------------------------------------
	@NotNull(message = "{validation.lecture.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer			lecturer;

}
