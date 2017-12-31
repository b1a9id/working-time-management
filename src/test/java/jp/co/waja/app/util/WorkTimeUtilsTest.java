package jp.co.waja.app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

}
