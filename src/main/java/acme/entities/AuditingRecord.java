
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditingRecord extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank(message = "{validation.auditingRecord.notNull}")
	@Length(max = 75)
	protected String			subject;

	@NotBlank(message = "{validation.auditingRecord.notNull}")
	@Length(max = 100)
	protected String			assessment;

	@NotNull(message = "{validation.auditingRecord.notNull}")
	protected MarkType			markType;

	@NotNull(message = "{validation.auditingRecord.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				initialPeriod;

	@NotNull(message = "{validation.auditingRecord.notNull}")
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				finalPeriod;

	@URL
	protected String			link;

	protected boolean			exceptional;

	//Relaciones
	@NotNull(message = "{validation.auditingRecord.notNull}")
	@Valid
	@ManyToOne(optional = false)
	protected Audit				audit;

}
