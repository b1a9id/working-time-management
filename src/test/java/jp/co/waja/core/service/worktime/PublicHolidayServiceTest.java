package jp.co.waja.core.service.worktime;

import jp.co.waja.core.entity.PublicHoliday;
import jp.co.waja.core.repository.worktime.PublicHolidayRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.Mock;

import java.time.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class PublicHolidayServiceTest {

	@InjectMocks
	private PublicHolidayService publicHolidayService;

	@Mock
	private PublicHolidayRepository publicHolidayRepository;

	@BeforeEach
	void setup() {
		initMocks(this);
	}

	@Nested
	class getPublicHolidays {
		@Test
		void success() {
			when(publicHolidayRepository.findAllByYearAndMonth(2017, 12)).thenReturn(generatePublicHoliday());

			List<LocalDate> publicHolidays = publicHolidayService.getPublicHolidays(YearMonth.of(2017, 12));

			Assertions.assertThat(publicHolidays)
					.extracting(LocalDate::getYear, LocalDate::getMonthValue, LocalDate::getDayOfMonth)
					.containsExactly(Tuple.tuple(2017, 1, 1), Tuple.tuple(2017, 12, 23));
		}
	}

	@Nested
	class isPublicHolidays {
		@Test
		void isHoliday() {
			when(publicHolidayRepository.existsPublicHolidayByYearAndMonthAndDayOfMonth(2017, 12, 23)).thenReturn(true);

			Assertions.assertThat(publicHolidayService.isPublicHolidays(LocalDate.of(2017, 12, 23)))
					.isTrue();

		}

		@Test
		void isNotHoliday() {
			when(publicHolidayRepository.existsPublicHolidayByYearAndMonthAndDayOfMonth(2017, 12, 10)).thenReturn(false);

			Assertions.assertThat(publicHolidayService.isPublicHolidays(LocalDate.of(2017, 12, 10)))
					.isFalse();

		}
	}

	List<PublicHoliday> generatePublicHoliday() {
		PublicHoliday p1 = new PublicHoliday(2017, 1, 1);
		PublicHoliday p2 = new PublicHoliday(2017, 12, 23);
		return Arrays.asList(p1, p2);
	}
}
