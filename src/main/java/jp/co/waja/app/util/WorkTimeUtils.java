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

	/**
	 * 指定月の1日から末日まで取得
	 * @param localDate
	 * @return
	 */
	public static List<LocalDate> getMonthDate(LocalDate localDate) {
		int thisMonthDays = localDate.lengthOfMonth();

		return IntStream.rangeClosed(1, thisMonthDays)
				.mapToObj(day -> LocalDate.of(localDate.getYear(), localDate.getMonth(), day))
				.collect(Collectors.toList());
	}
}
