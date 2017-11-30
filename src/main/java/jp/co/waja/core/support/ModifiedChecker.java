package jp.co.waja.core.support;

import jp.co.waja.core.entity.*;
import org.slf4j.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.*;

public class ModifiedChecker<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<History> check(T before, T after, String updatedBy) {
		List<History> histories = new ArrayList<>();
		if (isNull(before) || isNull(after)) {
			return histories;
		}

		LocalDateTime now = LocalDateTime.now();
		for (Field beforeField : before.getClass().getDeclaredFields()) {
			Field afterField;
			try {
				beforeField.setAccessible(true);
				afterField = after.getClass().getDeclaredField(beforeField.getName());
				afterField.setAccessible(true);
			} catch (NoSuchFieldException e) {
				logger.warn(e.getMessage());
				return histories;
			}

			String beforeValue;
			String afterValue;
			try {
				beforeValue = isNull(beforeField.get(before)) ? null : String.valueOf(beforeField.get(before));
				afterValue = isNull(afterField.get(after)) ? null : String.valueOf(afterField.get(after));
			} catch (IllegalAccessException e) {
				logger.warn(e.getMessage());
				return histories;
			}

			if (!beforeValue.equals(afterValue)) {
				History history = new History();
				history.setFieldName(beforeField.getName());
				history.setBeforeValue(beforeValue);
				history.setAfterValue(afterValue);
				history.setUpdatedBy(updatedBy);
				history.setUpdatedAt(now);

				histories.add(history);
			}
		}
		return histories;
	}
}
