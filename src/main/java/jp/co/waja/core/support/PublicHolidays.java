package jp.co.waja.core.support;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PublicHolidays {

	public static List<LocalDate> getPublicHolidays() {
		return Arrays.asList(
				LocalDate.of(2017, 1, 1),
				LocalDate.of(2017, 1, 2),
				LocalDate.of(2017, 1, 9),
				LocalDate.of(2017, 2, 11),
				LocalDate.of(2017, 3, 20),
				LocalDate.of(2017, 4, 29),
				LocalDate.of(2017, 5, 3),
				LocalDate.of(2017, 5, 4),
				LocalDate.of(2017, 5, 5),
				LocalDate.of(2017, 7, 17),
				LocalDate.of(2017, 8, 11),
				LocalDate.of(2017, 9, 18),
				LocalDate.of(2017, 9, 23),
				LocalDate.of(2017, 10, 9),
				LocalDate.of(2017, 11, 3),
				LocalDate.of(2017, 11, 23),
				LocalDate.of(2017, 12, 23)
		);
	}
}
