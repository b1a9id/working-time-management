package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.LongLeave;
import jp.co.waja.core.model.staff.LongLeaveRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class LongLeaveForm implements Serializable {

	@NotNull
	private LongLeave.Type type;

	@NotNull
	private LocalDate startAt;

	private LocalDate endAt;

	private String remarks;

	public LongLeaveRequest toLongLeaveRequest() {
		LongLeaveRequest request = new LongLeaveRequest();
		request.setType(getType());
		request.setStartAt(getStartAt());
		request.setEndAt(getEndAt());
		request.setRemarks(getRemarks());
		return request;
	}
}
