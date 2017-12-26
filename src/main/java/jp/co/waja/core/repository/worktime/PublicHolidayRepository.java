package jp.co.waja.core.repository.worktime;

import jp.co.waja.core.entity.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

	List<PublicHoliday> findAllByYearAndMonth(int year, int month);

	boolean existsPublicHolidayByYearAndMonthAndDayOfMonth(int year, int month, int dayOfMonth);
}
