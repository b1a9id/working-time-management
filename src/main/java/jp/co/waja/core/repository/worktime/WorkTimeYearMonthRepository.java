package jp.co.waja.core.repository.worktime;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTimeYearMonthRepository extends JpaRepository<WorkTimeYearMonth, Long> {
	WorkTimeYearMonth findOneByStaffAndWorkYearMonth(Staff staff, int yearMonth);

	WorkTimeYearMonth findOneByStaffAndId(Staff staff, Long id);

	List<WorkTimeYearMonth> findAllByStaff(Staff staff);

	long countByStaff(Staff staff);
}
