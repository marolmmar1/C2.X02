
package acme.entities;

import java.awt.geom.Arc2D.Double;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstracts;

	@Digits(integer = 3, fraction = 2)
	protected Double			estimatedTime;

	@NotBlank
	@Length(max = 100)
	protected String			body;

	@NotNull
	protected Nature			nature;

	@URL
	protected String			link;

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer			lecturer;

}
