
package acme.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(max = 100)
	protected String			conclusion;

	@NotBlank
	@Length(max = 100)
	protected String			strongPoints;

	@NotBlank
	@Length(max = 100)
	protected String			weakPoints;


	static String mode(final List<MarkType> a) {
		final int n = a.size();
		MarkType maxValue = MarkType.B;
		int maxCount = 0, i, j;
		for (i = 0; i < n; ++i) {
			int count = 0;
			for (j = 0; j < n; ++j)
				if (a.get(j) == a.get(i))
					++count;

			if (count > maxCount) {
				maxCount = count;
				maxValue = a.get(i);
			}
		}
		return maxValue.getName();
	}

}
