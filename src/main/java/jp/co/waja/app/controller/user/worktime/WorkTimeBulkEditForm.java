package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.service.worktime.WorkTimeBulkEditRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class WorkTimeBulkEditForm implements Serializable {

	private WorkTime.workType workType = WorkTime.workType.NORMAL;

	@NotNull
	private Integer startAtHour;

	@NotNull
	private Integer startAtMinute;

	@NotNull
	private Integer endAtHour;

	@NotNull
	private Integer endAtMinute;


	public WorkTimeBulkEditRequest toWorkTimeBulkEditRequest(LocalTime startAt, LocalTime endAt) {
		WorkTimeBulkEditRequest request = new WorkTimeBulkEditRequest();
		request.setWorkType(getWorkType());
		request.setStartAt(startAt);
		request.setEndAt(endAt);
		return request;
	}
}
