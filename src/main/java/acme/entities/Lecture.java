
package acme.entities;

import java.awt.geom.Arc2D.Double;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public class Lecture {

	@NotBlank
	@Length(max = 75)
	protected String		title;

	@NotBlank
	@Length(max = 100)
	protected String		abstracts;

	@Positive
	protected Double		estimatedTime;

	@NotBlank
	@Length(max = 100)
	protected String		body;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected LectureType	lectureType;

	@URL
	protected String		link;

}
