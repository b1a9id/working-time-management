package jp.co.waja.core.support;

import jp.co.waja.core.entity.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jp.co.waja.core.entity.WorkTime.WorkType.*;

/**
 * Created by uchitate on 2017/06/26.
 */
public class WorkTimeUtil {

	public static List<Integer> workTimeHour() {
		return IntStream.rangeClosed(6, 22).boxed().collect(Collectors.toList());
	}

	public static List<Integer> workTimeMinute() {
		return Arrays.asList(0, 15, 30, 45);
	}

	public static List<Integer> restTime() {
		return Arrays.asList(0, 45, 60, 75, 90, 120);
	}

	public static WorkTime.WorkType workType(LocalDate date) {
		if (date == null) {
			return null;
		}
		List<LocalDate> publicHolidays = PublicHolidays.getPublicHolidays();
		boolean isHoliday = publicHolidays.stream()
				.anyMatch(publicHoliday -> publicHoliday.isEqual(date));

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (isHoliday || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			return LEGAL_VACATION;
		}
		return NORMAL;
	}

	public static List<WorkTime.WorkType> workTypes(Staff staff) {
		if (staff == null) {
			return null;
		}
		if (!staff.isFlextime()) {
			return Arrays.asList(WorkTime.WorkType.values());
		}

		return Arrays.asList(
				NORMAL,
				LEGAL_VACATION,
				FULL_PAID_VACATION,
				HALF_PAID_VACATION,
				ABSENCE,
				HALF_ABSENCE,
				SPECIAL_VACATION
		);
	}

	public static Integer yearMonthToInt(YearMonth yearMonth) {
		if (yearMonth == null) {
			return null;
		}

		Integer year = yearMonth.getYear();
		Integer month = yearMonth.getMonthValue();
		return Integer.valueOf(year.toString() + month.toString());
	}

	public static YearMonth intToYearMonth(Integer yearMonth) {
		if (yearMonth == null) {
			return null;
		}

		String year = yearMonth.toString().substring(0, 4);
		String month = "0" + yearMonth.toString().substring(4);
		StringJoiner joiner = new StringJoiner("-");
		return YearMonth.parse(joiner.add(year).add(month).toString());
	}
}
