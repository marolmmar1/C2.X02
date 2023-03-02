
package acme.entities;

import java.awt.geom.Arc2D.Double;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class Lecture {

	@NotBlank
	@Length(max = 75)
	protected String	title;

	@NotBlank
	@Length(max = 100)
	protected String	_abstract;

	@Digits(integer = 3, fraction = 2)
	@Min((long) 0.01)
	protected Double	estimatedTime;

	@NotBlank
	@Length(max = 100)
	protected String	body;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Nature	nature;

	@URL
	protected String	link;

}
