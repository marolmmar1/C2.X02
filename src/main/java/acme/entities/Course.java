
package acme.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.components.datatypes.Money;
import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.course.notNull}")
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.course.code}")
	protected String			code;

	@NotBlank(message = "{validation.course.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.course.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@NotNull(message = "{validation.course.notNull}")
	protected Money				price;

	@URL
	protected String			link;

	protected boolean			draftMode;


	@Transient
	public Nature nature(final List<Lecture> lectures) {
		Nature res = Nature.BALANCE;
		if (!lectures.isEmpty()) {
			final Map<Nature, Integer> lecturesByNature = new HashMap<>();
			for (final Lecture lecture : lectures) {
				final Nature nature = lecture.getNature();
				if (lecturesByNature.containsKey(nature))
					lecturesByNature.put(nature, lecturesByNature.get(nature) + 1);
				else
					lecturesByNature.put(nature, 1);
			}
			if (lecturesByNature.containsKey(Nature.HANDS_ON) && lecturesByNature.containsKey(Nature.THEORETICAL))
				if (lecturesByNature.get(Nature.HANDS_ON) > lecturesByNature.get(Nature.THEORETICAL))
					res = Nature.HANDS_ON;
				else if (lecturesByNature.get(Nature.THEORETICAL) > lecturesByNature.get(Nature.HANDS_ON))
					res = Nature.THEORETICAL;
			if (lecturesByNature.containsKey(Nature.HANDS_ON) && !lecturesByNature.containsKey(Nature.THEORETICAL))
				res = Nature.HANDS_ON;
			if (!lecturesByNature.containsKey(Nature.HANDS_ON) && lecturesByNature.containsKey(Nature.THEORETICAL))
				res = Nature.THEORETICAL;
		}
		return res;
	}


	// Relationships ----------------------------------------------------------
	@NotNull(message = "{validation.course.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Lecturer lecturer;

}
