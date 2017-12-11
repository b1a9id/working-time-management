package jp.co.waja.core.support;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.Role;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import static java.util.Objects.*;
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

	public static boolean disabledApprove(WorkTimeYearMonth workTimeYearMonth, Role role, Team team) {
		if (role == Role.MANAGER && nonNull(workTimeYearMonth.getCompletedAt()) && isNull(workTimeYearMonth.getApproved1At())) {
			return false;
		}

		if (role == Role.ADMIN) {
			if (isNull(workTimeYearMonth.getApproved2At()) && workTimeYearMonth.getStaff().getTeam().equals(team)) {
				return false;
			}
			if (nonNull(workTimeYearMonth.getCompletedAt())
					&& nonNull(workTimeYearMonth.getApproved1At())
					&& isNull(workTimeYearMonth.getApproved2At())) {
				return false;
			}
		}
		return true;
	}

	public static boolean invalidEdit(WorkTimeYearMonth workTimeYearMonth) {
		if (!isNull(workTimeYearMonth.getCompletedAt()) && !isNull(workTimeYearMonth.getApproved1At())) {
			return false;
		}
		return true;
	}

	public static boolean isWeekDay(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	public static boolean isBusinessDay(WorkTime.WorkType workType) {
		if (isNull(workType)) {
			return false;
		}
		return workType == NORMAL || workType == HALF_PAID_VACATION || workType == HALF_PAID_VACATION_AFTER || workType == HALF_ABSENCE;
	}
}
