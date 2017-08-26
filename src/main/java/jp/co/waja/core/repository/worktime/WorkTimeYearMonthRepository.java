package jp.co.waja.core.repository.worktime;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTimeYearMonthRepository extends JpaRepository<WorkTimeYearMonth, Long> {
	WorkTimeYearMonth findOneByStaffAndWorkYearMonth(Staff staff, int yearMonth);
}
