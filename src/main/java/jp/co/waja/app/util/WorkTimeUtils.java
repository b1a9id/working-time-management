package jp.co.waja.app.util;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public final class WorkTimeUtils {

	public static Long workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		if (startAt == null || endAt == null || restTime == null) {
			return null;
		}
		return endAt.until(startAt, ChronoUnit.MINUTES) - restTime;
	}
}
