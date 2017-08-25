package jp.co.waja.core.service.worktime;

import jp.co.waja.core.entity.WorkTime;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class WorkTimeBulkEditRequest implements Serializable {

	private WorkTime.WorkType workType;

	private LocalTime startAt;

	private LocalTime endAt;
}
