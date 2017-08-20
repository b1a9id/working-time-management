package jp.co.waja.core.service.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.repository.worktime.WorkTimeRepository;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static jp.co.waja.core.entity.WorkTime.workType.NORMAL;

@Service
@Transactional
public class WorkTimeService {

	@Autowired
	private WorkTimeRepository workTimeRepository;

	public List<WorkTime> getWorkTimes(Staff staff, LocalDate localDate) {
		LocalDate startDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
		LocalDate endDate = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.lengthOfMonth());
		List<LocalDate> monthDates = WorkTimeUtils.getMonthDate();

		List<WorkTime> workTimes = getWorkTimes(startDate, endDate);
		Map<LocalDate, WorkTime> workTimeMap = new HashMap<>();
		workTimes.forEach(workTime -> workTimeMap.put(workTime.getDate(), workTime));

		List<WorkTime> nonInsertWorkTimes = monthDates.stream()
				.filter(monthDate -> Objects.isNull(workTimeMap.get(monthDate)))
				.map(monthDate -> {
					WorkTime.workType workType = WorkTimeUtil.workType(monthDate);
					return new WorkTime(staff, monthDate, workType);
				})
				.sorted()
				.collect(Collectors.toList());
		if (nonInsertWorkTimes.size() == 0) {
			return workTimes;
		}

		create(nonInsertWorkTimes);
		return getWorkTimes(startDate, endDate);
	}

	// TODO:ログインユーザもパラメータに設定
	public List<WorkTime> getWorkTimes(Staff staff) {
		return getWorkTimes(staff, LocalDate.now());
	}

	private List<WorkTime> getWorkTimes(LocalDate startDate, LocalDate endDate) {
		return workTimeRepository.findByDateBetween(startDate, endDate);
	}

	public List<WorkTime> create(List<WorkTime> workTimes) {
		List<WorkTime> savedWorkTimes = new ArrayList<>();
		for (WorkTime workTime : workTimes) {
			WorkTime savedWorkTime = workTimeRepository.saveAndFlush(workTime);
			savedWorkTimes.add(savedWorkTime);
		}
		return savedWorkTimes;
	}

	public int edit(Staff staff, String today, WorkTimeBulkEditRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parsedToday = LocalDate.parse(today, formatter);
		List<WorkTime> workTimes = getWorkTimes(staff, parsedToday);
		List<LocalDate> normalWorkTimes = workTimes.stream()
				.filter(workTime -> workTime.getWorkType() == NORMAL)
				.filter(workTime -> Objects.isNull(workTime.getStartAt()))
				.filter(workTime -> Objects.isNull(workTime.getEndAt()))
				.map(WorkTime::getDate)
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(normalWorkTimes)) {
			return 0;
		}
		return workTimeRepository.updateWorkTimes(request.getStartAt(), request.getEndAt(), normalWorkTimes);
	}
}
