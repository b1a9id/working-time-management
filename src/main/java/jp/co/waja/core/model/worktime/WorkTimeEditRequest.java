package jp.co.waja.core.model.worktime;

import jp.co.waja.core.entity.WorkTime;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WorkTimeEditRequest implements Serializable {
	private LocalDate date;

	private WorkTime.WorkType workType;

	private Boolean trainDelay;

	private LocalTime startAt;

	private LocalTime endAt;

	private Integer restTime;

	private String remarks;
}
