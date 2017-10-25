package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.staff.LongLeaveRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class LongLeaveForm implements Serializable {

	@NotNull
	private Long staffId;

	@NotNull
	private LongLeave.Type type;

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate startAt;

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate endAt;

	private String remarks;

	public LongLeaveRequest toLongLeaveRequest() {
		LongLeaveRequest request = new LongLeaveRequest();
		request.setStaffId(getStaffId());
		request.setType(getType());
		request.setStartAt(getStartAt());
		request.setEndAt(getEndAt());
		request.setRemarks(getRemarks());
		return request;
	}
}
