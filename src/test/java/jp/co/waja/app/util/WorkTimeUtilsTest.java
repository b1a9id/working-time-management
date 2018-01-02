package jp.co.waja.app.util;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
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

	@Nested
	class WorkTypeDays {
		@Test
		void isNormal() {
			String workTypeGroupStr = "NORMAL";
			BigDecimal result = WorkTimeUtils.workTypeDays(workTimeYearMonthProvider(), workTypeGroupStr);
			Assertions.assertEquals(BigDecimal.valueOf(3.0), result);
		}

		@DisplayName("結果が1.5となるグループ")
		@ParameterizedTest
		@ValueSource(strings = {"PAID_VACATION", "PAID_VACATION_AFTER", "ABSENCE"})
		void isNotNormalOfOnePointFiveGroups(String workTypeGroupStr) {
			BigDecimal result = WorkTimeUtils.workTypeDays(workTimeYearMonthProvider(), workTypeGroupStr);
			Assertions.assertEquals(BigDecimal.valueOf(1.5), result);
		}

		@DisplayName("結果が1.0となるグループ")
		@Test
		void isNotNormalOfOneGroups() {
			String workTypeGroupStr = "NORMAL_VACATION";
			BigDecimal result = WorkTimeUtils.workTypeDays(workTimeYearMonthProvider(), workTypeGroupStr);
			Assertions.assertEquals(BigDecimal.valueOf(1), result);
		}

		@DisplayName("結果が2.5となるグループ")
		@Test
		void isNotNormalOfTwoPointFiveGroups() {
			String workTypeGroupStr = "ILLEGAL_VACATION";
			BigDecimal result = WorkTimeUtils.workTypeDays(workTimeYearMonthProvider(), workTypeGroupStr);
			Assertions.assertEquals(BigDecimal.valueOf(2.5), result);
		}

		private WorkTimeYearMonth workTimeYearMonthProvider() {
			List<WorkTime> workTimes = Arrays.stream(WorkTime.WorkType.values())
					.map(type -> {
						WorkTime workTime = new WorkTime();
						workTime.setWorkType(type);
						return workTime;
					}).collect(Collectors.toList());
			WorkTimeYearMonth workTimeYearMonth = new WorkTimeYearMonth();
			workTimeYearMonth.setWorkTimes(workTimes);
			return workTimeYearMonth;
		}
	}

	@Nested
	class GetMonthDate {
		@Test
		void success() {
			List<LocalDate> result = WorkTimeUtils.getMonthDate(YearMonth.of(2017, 12));
			org.assertj.core.api.Assertions.assertThat(result)
					.contains(
							LocalDate.of(2017, 12, 1),
							LocalDate.of(2017, 12, 15),
							LocalDate.of(2017, 12, 31)
					)
					.extracting(LocalDate::getYear)
					.filteredOn(year -> year == 2017)
					.hasSize(31);
		}
	}

	@Nested
	class GetWorkTimeSum {

		List<WorkTime> workTimes;

		@BeforeEach
		void beforeEach() {
			List<WorkTime> works1 = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				WorkTime workTime = new WorkTime();
				workTime.setStartAt(LocalTime.of(9, 0));
				workTime.setEndAt(LocalTime.of(18, 0));
				workTime.setRestTime(60);
				works1.add(workTime);
			}

			List<WorkTime> vacations = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				vacations.add(new WorkTime());
			}

			List<WorkTime> works2 = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				WorkTime workTime = new WorkTime();
				workTime.setStartAt(LocalTime.of(10, 0));
				workTime.setEndAt(LocalTime.of(18, 0));
				workTime.setRestTime(60);
				works2.add(workTime);
			}

			workTimes = new ArrayList<>();
			workTimes.addAll(works1);
			workTimes.addAll(vacations);
			workTimes.addAll(works2);
		}

		@Test
		void success() {
			BigDecimal result = WorkTimeUtils.getWorkTimeSum(workTimes);
			BigDecimal expected = new BigDecimal(150.00).setScale(2, RoundingMode.UNNECESSARY);
			Assertions.assertEquals(expected, result);
		}
	}

	@Nested
	class Label {

		Map<WorkTime.WorkTypeGroup, String> workTypeGroupMap;

		@BeforeEach
		void beforeEach() {
			workTypeGroupMap = new HashMap<>();
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.NORMAL, "label-default");
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.NORMAL_VACATION, "label-danger");
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.PAID_VACATION, "label-success");
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.PAID_VACATION_AFTER, "label-success");
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.ABSENCE, "label-info");
			workTypeGroupMap.put(WorkTime.WorkTypeGroup.ILLEGAL_VACATION, "label-warning");
		}

		@ParameterizedTest
		@EnumSource(value = WorkTime.WorkType.class)
		void success(WorkTime.WorkType type) {
			WorkTime workTime = new WorkTime();
			workTime.setWorkType(type);
			String expected = workTypeGroupMap.get(type.getGroup());
			String result = WorkTimeUtils.label(workTime);
			Assertions.assertEquals(expected, result);
		}
	}

}
