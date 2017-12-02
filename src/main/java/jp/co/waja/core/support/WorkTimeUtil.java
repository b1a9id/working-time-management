package jp.co.waja.core.support;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.Role;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
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
		if (!isBusinessDay(date)) {
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

		String yearMonthStr = yearMonth.toString();
		String year = yearMonthStr.substring(0, 4);
		String month = yearMonthStr.substring(4).length() >= 2 ? yearMonthStr.substring(4) : "0" + yearMonthStr.substring(4);
		StringJoiner joiner = new StringJoiner("-");
		return YearMonth.parse(joiner.add(year).add(month).toString());
	}

	public static boolean disabledApprove(WorkTimeYearMonth workTimeYearMonth, Role role) {
		if (role == Role.MANAGER && !isNull(workTimeYearMonth.getCompletedAt()) && isNull(workTimeYearMonth.getApproved1At())) {
			return false;
		}

		if (role == Role.ADMIN
				&&!isNull(workTimeYearMonth.getCompletedAt())
					&& !isNull(workTimeYearMonth.getApproved1At())
						&& isNull(workTimeYearMonth.getApproved2At())) {
			return false;
		}
		return true;
	}

	public static boolean invalidEdit(WorkTimeYearMonth workTimeYearMonth) {
		if (!isNull(workTimeYearMonth.getCompletedAt()) && !isNull(workTimeYearMonth.getApproved1At())) {
			return false;
		}
		return true;
	}

	public static boolean isBusinessDay(LocalDate date) {
		if (isNull(date)) {
			return false;
		}
		List<LocalDate> publicHolidays = PublicHolidays.getPublicHolidays();
		boolean isHoliday = publicHolidays.stream()
				.anyMatch(publicHoliday -> publicHoliday.isEqual(date));

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return !isHoliday && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}
}
