package jp.co.waja.core.support;

import jp.co.waja.core.entity.WorkTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

	public static WorkTime.workType workType(LocalDate date) {
		List<LocalDate> publicHolidays = PublicHolidays.getPublicHolidays();
		boolean isHoliday = publicHolidays.stream()
				.anyMatch(publicHoliday -> publicHoliday.isEqual(date));

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if (isHoliday || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			return WorkTime.workType.LEGAL_VACATION;
		}
		return WorkTime.workType.NORMAL;
	}
}
