package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WorkTimeEditForm implements Serializable {
	@NotNull
	private Staff staff;

	@NotNull
	private LocalDate date;

	@NotNull
	private WorkTime.workType workType;

	private LocalTime startAt;

	private LocalTime endAt;

	@NotNull
	private Integer restTime;

	private String remarks;
}
