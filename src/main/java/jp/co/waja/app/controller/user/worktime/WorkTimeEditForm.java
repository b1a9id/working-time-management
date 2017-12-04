package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.*;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeEditForm implements Serializable {
	@NotNull
	private LocalDate date;

	@NotNull
	private WorkTime.WorkType workType;

	private Boolean trainDelay;

	private Integer startAtHour;

	private Integer startAtMinute;

	private Integer endAtHour;

	private Integer endAtMinute;
	
	private Integer restTime;

	private String remarks;

	public WorkTimeEditForm(WorkTime workTime) {
		this.date = workTime.getDate();
		this.workType = workTime.getWorkType();
		this.trainDelay = workTime.getTrainDelay();
		Optional<LocalTime> startAtOptional = Optional.ofNullable(workTime.getStartAt());
		startAtOptional.ifPresent(startAt -> {
			this.startAtHour = startAt.getHour();
			this.startAtMinute = startAt.getMinute();
		});
		Optional<LocalTime> endAtOptional = Optional.ofNullable(workTime.getEndAt());
		endAtOptional.ifPresent(endAt -> {
			this.endAtHour = endAt.getHour();
			this.endAtMinute = endAt.getMinute();
		});
		this.restTime = workTime.getRestTime();
		this.remarks = workTime.getRemarks();
	}
}
