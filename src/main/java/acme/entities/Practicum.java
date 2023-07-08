
package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.practicum.notNull}")
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.practicum.code}")
	protected String			code;

	@NotBlank(message = "{validation.practicum.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.practicum.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@NotBlank(message = "{validation.practicum.notNull}")
	@Length(max = 100)
	protected String			goals;

	protected boolean			draftMode;

	//Relaciones

	@NotNull(message = "{validation.practicum.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Company			company;

	@NotNull(message = "{validation.practicum.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;
}
