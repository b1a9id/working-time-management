package jp.co.waja.app.util;

import jp.co.waja.core.entity.WorkTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class WorkTimeUtils {

	public static BigDecimal workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		if (startAt == null || endAt == null || restTime == null) {
			return null;
		}

		long workTimeMinute = startAt.until(endAt, ChronoUnit.MINUTES) - restTime;
		BigDecimal workTime = BigDecimal.valueOf(workTimeMinute).divide(BigDecimal.valueOf(60), 2, RoundingMode.DOWN);
		return workTime;
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

	public static BigDecimal getWorkTimeSum(List<WorkTime> workTimes) {
		BigDecimal workTimeSum = BigDecimal.ZERO;
		for (WorkTime workTime : workTimes) {
			BigDecimal time = Optional.ofNullable(workTime(workTime.getStartAt(), workTime.getEndAt(), workTime.getRestTime())).orElse(BigDecimal.ZERO);
			workTimeSum = workTimeSum.add(time);
		}
		return workTimeSum;
	}
}
