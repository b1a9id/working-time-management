package jp.co.waja.core.repository.worktime;

import jp.co.waja.core.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
	List<WorkTime> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
