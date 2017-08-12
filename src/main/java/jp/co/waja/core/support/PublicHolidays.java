package jp.co.waja.core.support;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PublicHolidays {

	public static List<LocalDate> getPublicHolidays() {
		int thisYear = LocalDate.now().getYear();
		return Arrays.asList(
				LocalDate.of(thisYear, 1, 1),
				LocalDate.of(thisYear, 1, 2),
				LocalDate.of(thisYear, 1, 9),
				LocalDate.of(thisYear, 2, 11),
				LocalDate.of(thisYear, 3, 20),
				LocalDate.of(thisYear, 4, 29),
				LocalDate.of(thisYear, 5, 3),
				LocalDate.of(thisYear, 5, 4),
				LocalDate.of(thisYear, 5, 5),
				LocalDate.of(thisYear, 7, 17),
				LocalDate.of(thisYear, 8, 11),
				LocalDate.of(thisYear, 9, 18),
				LocalDate.of(thisYear, 9, 23),
				LocalDate.of(thisYear, 10, 9),
				LocalDate.of(thisYear, 11, 3),
				LocalDate.of(thisYear, 11, 23),
				LocalDate.of(thisYear, 12, 23)
		);
	}
}
