
package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CourseLecture extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Relationships ----------------------------------------------------------
	@NotNull(message = "{validation.courseLecture.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@NotNull(message = "{validation.courseLecture.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Lecture			lecture;

}
