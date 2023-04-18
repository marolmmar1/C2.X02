
package acme.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumMode {

	public static <T extends Enum<T>> T mode(final List<T> enumList) {
		final Map<T, Integer> countMap = new HashMap<>();
		T mode = null;
		int maxCount = 0;

		for (final T enumValue : enumList) {
			final int count = countMap.getOrDefault(enumValue, 0) + 1;
			countMap.put(enumValue, count);

			if (count > maxCount) {
				mode = enumValue;
				maxCount = count;
			}
		}

		return mode;
	}

}
