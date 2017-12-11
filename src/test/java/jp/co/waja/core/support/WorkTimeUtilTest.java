package jp.co.waja.core.support;

import jp.co.waja.core.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.*;
import java.util.*;

import static jp.co.waja.core.entity.WorkTime.WorkType.*;

public class WorkTimeUtilTest {

	/**
	 * 入っている値と順番を検証
	 */
	@Test
	public void workTimeHour() {
		List<Integer> result = WorkTimeUtil.workTimeHour();
		Assertions.assertThat(result)
				.containsExactly(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
	}

	@Test
	public void workTimeMinute() {
		List<Integer> result = WorkTimeUtil.workTimeMinute();
		Assertions.assertThat(result)
				.containsExactly(0, 15, 30, 45);
	}

	@Test
	public void restTime() {
		List<Integer> result = WorkTimeUtil.restTime();
		Assertions.assertThat(result)
				.containsExactly(0, 45, 60, 75, 90, 120);
	}

	@Test
	public void workTypesNotFlextime() {
		List<WorkTime.WorkType> result = WorkTimeUtil.workTypes(generateStaff(false));
		Assertions.assertThat(result)
				.containsExactly(
						NORMAL,
						LEGAL_VACATION,
						FULL_PAID_VACATION,
						HALF_PAID_VACATION,
						FULL_PAID_VACATION_AFTER,
						HALF_PAID_VACATION_AFTER,
						ABSENCE,
						HALF_ABSENCE,
						COMPENSATORY_VACATION,
						SPECIAL_VACATION);
	}

	@Test
	public void workTypesFlextime() {
		List<WorkTime.WorkType> result = WorkTimeUtil.workTypes(generateStaff(true));
		Assertions.assertThat(result)
				.containsExactly(
						NORMAL,
						LEGAL_VACATION,
						FULL_PAID_VACATION,
						HALF_PAID_VACATION,
						ABSENCE,
						HALF_ABSENCE,
						SPECIAL_VACATION
				);
	}

	@Test
	public void yearMonthToInt() {
		int result = WorkTimeUtil.yearMonthToInt(YearMonth.of(2017, 9));
		Assertions.assertThat(result)
				.isEqualTo(20179);
	}

	private Staff generateStaff(boolean flextime) {
		Staff staff = new Staff();
		staff.setFlextime(flextime);
		return staff;
	}
}
