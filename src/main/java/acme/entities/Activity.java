
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.activity.notNull}")
	@Length(max = 75)
	protected String			title;

	@NotBlank(message = "{validation.activity.notNull}")
	@Length(max = 100)
	protected String			abstracts;

	@NotNull(message = "{validation.activity.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				inicialPeriod;

	@NotNull(message = "{validation.activity.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				finalPeriod;

	@NotNull(message = "{validation.activity.notNull}")
	protected Nature			nature;

	@URL
	protected String			link;

	//Relaciones

	@NotNull(message = "{validation.activity.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Enrolment			enrolment;

}
