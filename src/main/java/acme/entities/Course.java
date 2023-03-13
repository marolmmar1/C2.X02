
package acme.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.roles.Company;
import acme.roles.Student;

public class Course {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.course.code}")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstracts;

	@NotNull
	protected Money				price;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Nature			nature;

	@URL
	protected String			link;
	
}
