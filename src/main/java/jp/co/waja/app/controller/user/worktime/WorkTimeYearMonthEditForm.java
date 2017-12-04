package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.worktime.WorkTimeEditRequest;
import jp.co.waja.core.model.worktime.WorkTimeYearMonthEditRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static jp.co.waja.core.entity.WorkTime.WorkType.*;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeYearMonthEditForm implements Serializable {
	private Long id;

	@NotNull
	@Valid
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
				.map(this::toWorkTimeEditRequest)
				.collect(Collectors.toList());
		request.setId(getId());
		request.setWorkTimes(editRequests);
		return request;
	}

	private WorkTimeEditRequest toWorkTimeEditRequest(WorkTimeEditForm workTime) {
		WorkTimeEditRequest editRequest = new WorkTimeEditRequest();
		editRequest.setDate(workTime.getDate());
		editRequest.setWorkType(workTime.getWorkType());
		editRequest.setTrainDelay(workTime.getTrainDelay());

		List<WorkTime.WorkType> nonTimeWorkTypes = Arrays.asList(
				LEGAL_VACATION, FULL_PAID_VACATION, ABSENCE, COMPENSATORY_VACATION, SPECIAL_VACATION
		);
		boolean hasTimeWorkType = !nonTimeWorkTypes.contains(workTime.getWorkType());

		boolean hasStartTime = hasTimeWorkType && nonNull(workTime.getStartAtHour()) && nonNull(workTime.getStartAtMinute());
		LocalTime startAt = hasStartTime ? LocalTime.of(workTime.getStartAtHour(), workTime.getEndAtMinute()) : null;
		editRequest.setStartAt(startAt);
		boolean hasEndTime = hasTimeWorkType && nonNull(workTime.getEndAtHour()) && nonNull(workTime.getEndAtMinute());
		LocalTime endAt = hasEndTime ? LocalTime.of(workTime.getEndAtHour(), workTime.getEndAtMinute()) : null;
		editRequest.setEndAt(endAt);

		Integer restTime = hasTimeWorkType ? workTime.getRestTime() : null;
		editRequest.setRestTime(restTime);
		editRequest.setRemarks(workTime.getRemarks());
		return editRequest;
	}
}
