
package acme.datatypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatData {

	Double	sum;
	Double	average;
	Double	desviation;
	Double	minimum;
	Double	maximum;


	public StatData(final Double sum, final Double average, final Double desviation, final Double minimum, final Double maximum) {
		this.sum = sum;
		this.average = average;
		this.desviation = desviation;
		this.minimum = minimum;
		this.maximum = maximum;
	}

}
