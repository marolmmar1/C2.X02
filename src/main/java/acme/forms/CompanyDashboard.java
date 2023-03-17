
package acme.forms;

import java.util.Map;

import acme.datatypes.StatData;
import acme.entities.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	protected static final long		serialVersionUID	= 1L;

	protected Map<Nature, Integer>	naturePerCourseLastYear;

	protected StatData				periodLengthOfSessionsStat;

	protected StatData				periodLengthOfPracticaStat;

}
