
package acme.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import acme.framework.helpers.MomentHelper;

public class DateChecker {

	public static boolean isDateInFuture(final String dateString) {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
		Date date;

		try {
			date = sdf.parse(dateString);

			// Get the current date
			final Date currentDate = MomentHelper.getCurrentMoment();

			// Compare the parsed date with the current date
			return date.after(currentDate);
		} catch (final ParseException e) {
			//e.printStackTrace();
		}

		// Return false if there's an error parsing the date
		return false;
	}
}
