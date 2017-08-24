package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
public class WorkTimeEditForm implements Serializable {
	@NotNull
	private Long id;

	@NotNull
	private WorkTime.workType workType;

	private LocalTime startAt;

	private LocalTime endAt;

	@NotNull
	private Integer restTime;

	private String remarks;

	public WorkTimeEditForm(WorkTime workTime) {
		this.id = workTime.getId();
		this.workType = workTime.getWorkType();
		this.startAt = workTime.getStartAt();
		this.endAt = workTime.getEndAt();
		this.restTime = workTime.getRestTime();
		this.remarks = workTime.getRemarks();
	}
}
