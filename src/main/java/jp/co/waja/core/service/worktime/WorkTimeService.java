package jp.co.waja.core.service.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.repository.worktime.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkTimeService {

	@Autowired
	private WorkTimeRepository workTimeRepository;

	// TODO:ログインユーザもパラメータに設定
	public List<WorkTime> workTimesThisMonth(List<LocalDate> thisMonthDates) {
		return thisMonthDates.stream()
				.map(thisMonthDate -> {
					WorkTime workTime = workTimeRepository.findOneByDate(thisMonthDate);
					return Optional.ofNullable(workTime).orElse(new WorkTime(thisMonthDate));
				})
				.collect(Collectors.toList());
	}
}
