package jp.co.waja.core.service.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.repository.worktime.WorkTimeRepository;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkTimeService {

	@Autowired
	private WorkTimeRepository workTimeRepository;

	// TODO:ログインユーザもパラメータに設定
	public List<WorkTime> workTimesThisMonth() {
		LocalDate now = LocalDate.now();
		LocalDate startDate = LocalDate.of(now.getYear(), now.getMonth(), 1);
		LocalDate endDate = LocalDate.of(now.getYear(), now.getMonth(), now.lengthOfMonth());
		List<LocalDate> monthDates = WorkTimeUtils.getMonthDate();

		List<WorkTime> workTimes = workTimeRepository.findByDateBetween(startDate, endDate);
		Map<LocalDate, WorkTime> workTimeMap = new HashMap<>();
		workTimes.forEach(workTime -> workTimeMap.put(workTime.getDate(), workTime));

		return monthDates.stream()
				.map(monthDate -> {
					WorkTime workTime = workTimeMap.get(monthDate);
					if (workTime != null) {
						return workTime;
					}
					WorkTime.workType workType = WorkTimeUtil.workType(monthDate);
					return new WorkTime(monthDate, workType);
				})
				.sorted()
				.collect(Collectors.toList());
	}
}
