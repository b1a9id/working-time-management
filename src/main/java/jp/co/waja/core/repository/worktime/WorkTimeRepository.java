package jp.co.waja.core.repository.worktime;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
	List<WorkTime> findByStaffAndDateBetween(Staff staff, LocalDate startDate, LocalDate endDate);

	@Modifying
	@Query("update WorkTime w set w.startAt = :startAt, w.endAt = :endAt where w.date in :dates and w.staff = :staff")
	int updateWorkTimes(@Param("staff") Staff staff, @Param("startAt")LocalTime startAt, @Param("endAt")LocalTime endAt, @Param("dates")List<LocalDate> workTimeDates);
}
