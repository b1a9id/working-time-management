package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.worktime.WorkTimeEditRequest;
import jp.co.waja.core.model.worktime.WorkTimeYearMonthEditRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonthEditForm implements Serializable {
	private Long id;

	private List<WorkTimeEditForm> workTimes;

	public WorkTimeYearMonthEditForm(WorkTimeYearMonth workTimeYearMonth) {
		this.id = workTimeYearMonth.getId();
		this.workTimes = workTimeYearMonth.getWorkTimes().stream()
				.map(WorkTimeEditForm::new)
				.collect(Collectors.toList());
	}

	public WorkTimeYearMonthEditRequest toWorkTimeYearMonthEditRequest() {
		WorkTimeYearMonthEditRequest request = new WorkTimeYearMonthEditRequest();
		List<WorkTimeEditRequest> editRequests = getWorkTimes().stream()
				.map(workTime -> {
					WorkTimeEditRequest editRequest = new WorkTimeEditRequest();
					editRequest.setDate(workTime.getDate());
					editRequest.setWorkType(workTime.getWorkType());
					if (nonNull(workTime.getStartAtHour()) && nonNull(workTime.getStartAtMinute())) {
						LocalTime startAt = LocalTime.of(workTime.getStartAtHour(), workTime.getEndAtMinute());
						editRequest.setStartAt(startAt);
					}
					if (nonNull(workTime.getEndAtHour()) && nonNull(workTime.getEndAtMinute())) {
						LocalTime endAt = LocalTime.of(workTime.getEndAtHour(), workTime.getEndAtMinute());
						editRequest.setEndAt(endAt);
					}
					editRequest.setRestTime(workTime.getRestTime());
					editRequest.setRemarks(workTime.getRemarks());
					return editRequest;
				}).collect(Collectors.toList());
		request.setId(getId());
		request.setWorkTimes(editRequests);
		return request;
	}
}
