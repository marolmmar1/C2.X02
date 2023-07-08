
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecturer extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.lecturer.notNull}")
	@Length(max = 75)
	protected String			almaMater;

	@NotBlank(message = "{validation.lecturer.notNull}")
	@Length(max = 100)
	protected String			qualifications;

	@NotBlank(message = "{validation.lecturer.notNull}")
	@Length(max = 100)
	protected String			resume;

	@URL
	protected String			link;

}
