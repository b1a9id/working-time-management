package jp.co.waja.app.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class WorkTimeUtils {

	public static Long workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		if (startAt == null || endAt == null || restTime == null) {
			return null;
		}
		return endAt.until(startAt, ChronoUnit.MINUTES) - restTime;
	}

	public static List<LocalDate> getMonthDate() {
		LocalDate today = LocalDate.now();
		int thisMonthDays = today.lengthOfMonth();

		return IntStream.rangeClosed(1, thisMonthDays)
				.mapToObj(day -> LocalDate.of(today.getYear(), today.getMonth(), day))
				.collect(Collectors.toList());
	}
}
