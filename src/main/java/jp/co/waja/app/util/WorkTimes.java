package jp.co.waja.app.util;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.support.WorkTimeUtil;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.YearMonth;

public class WorkTimes {

	public BigDecimal workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		return WorkTimeUtils.workTime(startAt, endAt, restTime);
	}

	public boolean overTime(BigDecimal workTime) {
		return WorkTimeUtils.overTime(workTime);
	}

	public String formattedYearMonth(Integer yearMonthInt) {
		return WorkTimeUtils.formattedYearMonth(yearMonthInt);
	}

	public BigDecimal workTypeDays(WorkTimeYearMonth workTimeYearMonth, String workType) {
		return WorkTimeUtils.workTypeDays(workTimeYearMonth, workType);
	}

	public YearMonth intToYearMonth(Integer yearMonthInt) {
		return WorkTimeUtil.intToYearMonth(yearMonthInt);
	}

	public boolean disabledApprove(WorkTimeYearMonth workTimeYearMonth, Role role, Team team) {
		return WorkTimeUtil.disabledApprove(workTimeYearMonth, role, team);
	}

	public boolean invalidEdit(WorkTimeYearMonth workTimeYearMonth) {
		return WorkTimeUtil.invalidEdit(workTimeYearMonth);
	}

	public boolean isNotBusinessDay(WorkTime workTime) {
		return !WorkTimeUtil.isBusinessDay(workTime.getWorkType());
	}

	public String label(WorkTime workTime) {
		return WorkTimeUtils.label(workTime);
	}
}
