package jp.co.waja.app.util;

import java.math.BigDecimal;
import java.time.LocalTime;

public class WorkTimes {

	public BigDecimal workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		return WorkTimeUtils.workTime(startAt, endAt, restTime);
	}
}
