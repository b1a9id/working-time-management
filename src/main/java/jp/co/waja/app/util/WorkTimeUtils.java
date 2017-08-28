package jp.co.waja.app.util;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.support.WorkTimeUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

	public static String formattedYearMonth(Integer yearMonthInt) {
		YearMonth yearMonth = WorkTimeUtil.intToYearMonth(yearMonthInt);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
		return formatter.format(yearMonth);
	}

	/**
	 * 指定月の1日から末日まで取得
	 * @param yearMonth
	 * @return
	 */
	public static List<LocalDate> getMonthDate(YearMonth yearMonth) {
		int thisMonthDays = yearMonth.lengthOfMonth();

		return IntStream.rangeClosed(1, thisMonthDays)
				.mapToObj(day -> LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), day))
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

	public static Map<WorkTime.WorkType, Long> workTypeCount(List<WorkTime> workTimes) {
		List<WorkTime.WorkType> workTypes =workTimes.stream()
				.map(WorkTime::getWorkType)
				.collect(Collectors.toList());

		Map<WorkTime.WorkType, Long> workTypeCountMap = new HashMap<>();
		workTypes.forEach(workType -> workTypeCountMap.put(workType, workTypeCount(workTimes, workType)));
		return workTypeCountMap;
	}

	public static long workTypeCount(List<WorkTime> workTimes, WorkTime.WorkType workType) {
		return workTimes.stream()
				.filter(workTime -> workTime.getWorkType() == workType)
				.count();
	}
}
