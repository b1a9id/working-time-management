package jp.co.waja.core.support;

import jp.co.waja.core.entity.WorkTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by uchitate on 2017/06/26.
 */
public class WorkTimeUtil {

	public static List<Integer> workTimeHour() {
		return IntStream.rangeClosed(6, 22).boxed().collect(Collectors.toList());
	}

	public static List<Integer> workTimeMinute() {
		return Arrays.asList(0, 15, 45);
	}

	public static List<Integer> restTime() {
		return Arrays.asList(0, 45, 60, 75, 90, 120);
	}

	public static WorkTime.WorkType workType(LocalDate date) {
		List<LocalDate> publicHolidays = PublicHolidays.getPublicHolidays();
		boolean isHoliday = publicHolidays.stream()
				.anyMatch(publicHoliday -> publicHoliday.isEqual(date));

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (isHoliday || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			return WorkTime.WorkType.LEGAL_VACATION;
		}
		return WorkTime.WorkType.NORMAL;
	}

	public static int yearMonthToInt(YearMonth yearMonth) {
		Integer year = yearMonth.getYear();
		Integer month = yearMonth.getMonthValue();
		return Integer.valueOf(year.toString() + month.toString());
	}

	public static YearMonth intToYearMonth(Integer yearMonth) {
		String year = yearMonth.toString().substring(0, 4);
		String month = "0" + yearMonth.toString().substring(4);
		StringJoiner joiner = new StringJoiner("-");
		return YearMonth.parse(joiner.add(year).add(month).toString());
	}
}
