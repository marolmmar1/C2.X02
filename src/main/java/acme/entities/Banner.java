
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@PastOrPresent
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@NotNull
	protected Date				period;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			slogan;

	@URL
	protected String			link;

}
