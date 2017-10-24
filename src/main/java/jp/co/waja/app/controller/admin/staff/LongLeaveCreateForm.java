package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.model.staff.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LongLeaveCreateForm implements Serializable {

	@NotNull
	private List<LongLeaveForm> forms;

	public LongLeaveCreateRequest toLongLeaveCreateRequest() {
		List<LongLeaveRequest> requests = getForms().stream()
				.map(LongLeaveForm::toLongLeaveRequest)
				.collect(Collectors.toList());
		LongLeaveCreateRequest createRequest = new LongLeaveCreateRequest();
		createRequest.setLongLeaveRequests(requests);
		return createRequest;
	}
}
