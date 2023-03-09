
package acme.entities;

import java.awt.geom.Arc2D.Double;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9]{3}", message = "{validation.tutorial.code}")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			_abstract;

	@NotBlank
	@Length(max = 100)
	protected String			goals;

	@Digits(integer = 3, fraction = 2)
	@Min((long) 0.01)
	protected Double			estimatedTime;

}
