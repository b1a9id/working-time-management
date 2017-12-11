package jp.co.waja.core.service.worktime;

import jp.co.waja.core.entity.PublicHoliday;
import jp.co.waja.core.repository.worktime.PublicHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicHolidayService {

	@Autowired
	private PublicHolidayRepository publicHolidayRepository;

	public List<LocalDate> getPublicHolidays(YearMonth yearMonth) {
		List<PublicHoliday> publicHolidays = publicHolidayRepository.findAllByYearAndMonth(yearMonth.getYear(), yearMonth.getMonthValue());
		return publicHolidays.stream()
				.map(publicHoliday -> LocalDate.of(publicHoliday.getYear(), publicHoliday.getMonth(), publicHoliday.getDayOfMonth()))
				.collect(Collectors.toList());
	}

	public boolean isPublicHolidays(LocalDate date) {
		return publicHolidayRepository.existsPublicHolidayByYearAndMonthAndDayOfMonth(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	}
}
