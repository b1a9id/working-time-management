package jp.co.waja.core.service.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.repository.worktime.WorkTimeYearMonthRepository;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkTimeService {

	@Autowired
	private WorkTimeYearMonthRepository workTimeYearMonthRepository;

	public WorkTimeYearMonth getWorkTimeYearMonth(Staff staff, int yearMonth) {
		return workTimeYearMonthRepository.findOneByStaffAndWorkYearMonth(staff, yearMonth);
	}

	public WorkTimeYearMonth createWorkTimeYearMonth(Staff staff, int yearMonth) {
		WorkTimeYearMonth workTimeYearMonth = new WorkTimeYearMonth(staff, yearMonth);
		List<WorkTime> workTimes = createWorkTimes(workTimeYearMonth);
		workTimeYearMonth.setWorkTimes(workTimes);
		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	private List<WorkTime> createWorkTimes(WorkTimeYearMonth workTimeYearMonth) {
		Integer yearMonthInt = workTimeYearMonth.getWorkYearMonth();
		List<LocalDate> monthDates = WorkTimeUtils.getMonthDate(WorkTimeUtil.intToYearMonth(yearMonthInt));

		return monthDates.stream()
				.map(monthDate -> {
					WorkTime workTime = new WorkTime();
					workTime.setDate(monthDate);
					WorkTime.WorkType workType = WorkTimeUtil.workType(monthDate);
					workTime.setWorkType(workType);
					return workTime;
				}).collect(Collectors.toList());
	}

	public WorkTimeYearMonth bulkEdit(Staff staff, String displayYearMonth, WorkTimeBulkEditRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth parsedDisplayYearMonth = YearMonth.parse(displayYearMonth, formatter);
		WorkTimeYearMonth workTimeYearMonth = getWorkTimeYearMonth(staff, WorkTimeUtil.yearMonthToInt(parsedDisplayYearMonth));
		workTimeYearMonth.getWorkTimes().forEach(
				workTime -> {
					if (workTime.getWorkType() == WorkTime.WorkType.NORMAL) {
						workTime.setStartAt(request.getStartAt());
						workTime.setEndAt(request.getEndAt());
						workTime.setRestTime(60);
					}
				});
		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}
}
