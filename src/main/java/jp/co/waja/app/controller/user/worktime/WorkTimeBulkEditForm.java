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

	@NotNull
	private WorkTime.workType workType;

	@NotNull
	private LocalTime startAt;

	@NotNull
	private LocalTime endAt;

	public WorkTimeBulkEditRequest toWorkTimeBulkEditRequest() {
		WorkTimeBulkEditRequest request = new WorkTimeBulkEditRequest();
		request.setWorkType(getWorkType());
		request.setStartAt(getStartAt());
		request.setEndAt(getEndAt());
		return request;
	}
}
