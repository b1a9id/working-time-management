package jp.co.waja.app.util;

import jp.co.waja.core.support.WorkTimeUtil;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.YearMonth;

public class WorkTimes {

	public BigDecimal workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		return WorkTimeUtils.workTime(startAt, endAt, restTime);
	}

	public String formattedYearMonth(Integer yearMonthInt) {
		return WorkTimeUtils.formattedYearMonth(yearMonthInt);
	}

	public YearMonth intToYearMonth(Integer yearMonthInt) {
		return WorkTimeUtil.intToYearMonth(yearMonthInt);
	}
}
