
package acme.datatypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatData {

	int		count;
	Double	average;
	Double	desviation;
	Double	minimum;
	Double	maximum;


	public StatData() {
	}

	public StatData(final int count, final Double average, final Double desviation, final Double minimum, final Double maximum) {
		super();
		this.count = count;
		this.average = average;
		this.desviation = desviation;
		this.minimum = minimum;
		this.maximum = maximum;
	}

}
