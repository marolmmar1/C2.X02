
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
public class Assistant extends AbstractRole {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.assistant.notNull}")
	@Length(max = 75)
	protected String			supervisor;

	@NotBlank(message = "{validation.assistant.notNull}")
	@Length(max = 100)
	protected String			expertiseField;

	@NotBlank(message = "{validation.assistant.notNull}")
	@Length(max = 100)
	protected String			resume;

	@URL
	protected String			link;

}
