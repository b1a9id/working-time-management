package jp.co.waja.core.support;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static jp.co.waja.core.entity.WorkTime.WorkType.*;

class WorkTimeUtilTest {

	@Nested
	class WorkTimeHour {
		@DisplayName("入っている値と順番を検証")
		@Test
		void workTimeHour() {
			List<Integer> result = WorkTimeUtil.workTimeHour();
			Assertions.assertThat(result)
					.containsExactly(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
		}
	}

	@Nested
	class WorkTimeMinute {
		@Test
		void workTimeMinute() {
			List<Integer> result = WorkTimeUtil.workTimeMinute();
			Assertions.assertThat(result)
					.containsExactly(0, 15, 30, 45);
		}
	}

	@Nested
	class RestTime {
		@Test
		void restTime() {
			List<Integer> result = WorkTimeUtil.restTime();
			Assertions.assertThat(result)
					.containsExactly(0, 45, 60, 75, 90, 120);
		}
	}

	@Nested
	class WorkTypes {
		@Test
		void staffNull() {
			Assertions.assertThat(WorkTimeUtil.workTypes(null))
					.isNull();
		}

		@Test
		void notFlextime() {
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
							HALF_COMPENSATORY_VACATION,
							SPECIAL_VACATION);
		}

		@Test
		void isFlextime() {
			List<WorkTime.WorkType> result = WorkTimeUtil.workTypes(generateStaff(true));
			Assertions.assertThat(result)
					.containsExactly(
							NORMAL,
							LEGAL_VACATION,
							FULL_PAID_VACATION,
							HALF_PAID_VACATION,
							ABSENCE,
							HALF_ABSENCE,
							COMPENSATORY_VACATION,
							HALF_COMPENSATORY_VACATION,
							SPECIAL_VACATION
					);
		}

		private Staff generateStaff(boolean flextime) {
			Staff staff = new Staff();
			staff.setFlextime(flextime);
			return staff;
		}
	}

	@Nested
	class YearMonthToInt {
		@Test
		void yearMonthNull() {
			Assertions.assertThat(WorkTimeUtil.yearMonthToInt(null))
					.isNull();
		}

		@Test
		void yearMonthToInt() {
			int result = WorkTimeUtil.yearMonthToInt(YearMonth.of(2017, 9));
			Assertions.assertThat(result)
					.isEqualTo(20179);
		}
	}

	@ParameterizedTest
	@MethodSource("notWeekDayProvider")
	void isNotWeekDay(LocalDate target) {
		Assertions.assertThat(WorkTimeUtil.isWeekDay(target))
				.isFalse();
	}

	static Stream<LocalDate> notWeekDayProvider() {
		return IntStream.rangeClosed(25, 29)
				.mapToObj(date -> LocalDate.of(2017, 12, date));
	}

	@ParameterizedTest
	@MethodSource("weekDayProvider")
	void isWeekDay(LocalDate target) {
		Assertions.assertThat(WorkTimeUtil.isWeekDay(target))
				.isTrue();
	}

	static Stream<LocalDate> weekDayProvider() {
		return IntStream.rangeClosed(30, 31)
				.mapToObj(date -> LocalDate.of(2017, 12, date));
	}


}
