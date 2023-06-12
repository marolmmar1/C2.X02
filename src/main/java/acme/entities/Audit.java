
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
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.audit.notNull}")
	@Column(unique = true)
	@Pattern(regexp = "(^[A-Z]{1,3}[0-9]{3}$)", message = "{validation.audit.code}")
	protected String			code;

	@NotBlank(message = "{validation.audit.notNull}")
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank(message = "{validation.audit.notNull}")
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank(message = "{validation.audit.notNull}")
	@Length(max = 100)
	protected String			weakPoints;

	protected boolean			draftMode;

	//Relaciones

	@NotNull(message = "{validation.audit.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Auditor			auditor;

	@NotNull(message = "{validation.audit.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

}
