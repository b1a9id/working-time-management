package jp.co.waja.core.model.worktime;

import jp.co.waja.core.entity.WorkTime;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class WorkTimeEditRequest implements Serializable {
	private Long id;

	private WorkTime.workType workType;

	private LocalTime startAt;

	private LocalTime endAt;

	private Integer restTime;

	private String remarks;
}
