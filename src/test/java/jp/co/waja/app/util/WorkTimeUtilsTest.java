package jp.co.waja.app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.stream.Stream;

class WorkTimeUtilsTest {

	@ParameterizedTest
	@MethodSource("localTimeAndIntegerProvider")
	void returnNull(LocalTime startAt, LocalTime endAt, Integer restTime) {
		Assertions.assertNull(WorkTimeUtils.workTime(startAt, endAt, restTime));
	}

	static Stream<Arguments> localTimeAndIntegerProvider() {
		LocalTime startAt = LocalTime.of( 9, 0);
		LocalTime endAt = LocalTime.of(18, 0);
		int restTime = 60;
		return Stream.of(
				Arguments.of(null, endAt, restTime),
				Arguments.of(startAt, null, restTime),
				Arguments.of(startAt, endAt, null),
				Arguments.of(null, null, restTime),
				Arguments.of(null, endAt, null),
				Arguments.of(startAt, null, null),
				Arguments.of(null, null, null)
		);
	}

	@Nested
	class OverTime {
		@Test
		void workTimeNull() {
			Assertions.assertFalse(WorkTimeUtils.overTime(null));
		}

		@Test
		void isTrue() {
			Assertions.assertTrue(WorkTimeUtils.overTime(new BigDecimal(9)));
		}

		@Test
		void isFalse() {
			Assertions.assertFalse(WorkTimeUtils.overTime(new BigDecimal(8)));
		}
	}

	@Nested
	class FormattedYearMonth {
		@Test
		void formattedYearMonth() {
			Assertions.assertEquals("2017年12月", WorkTimeUtils.formattedYearMonth(201712));
		}
	}

}
