
package acme.entities;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CourseLecture {

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course	course;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Lecture	lecture;

}
