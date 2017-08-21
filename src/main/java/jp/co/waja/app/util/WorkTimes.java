package jp.co.waja.app.util;

import java.time.LocalTime;

public class WorkTimes {

	public Long workTime(LocalTime startAt, LocalTime endAt, Integer restTime) {
		return WorkTimeUtils.workTime(startAt, endAt, restTime);
	}
}
