package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class StaffSearchForm implements Serializable {

	private Long teamId;

	private Staff.EmploymentType employmentType;

	private LocalDate enteredDate;

	private boolean telework;

	private boolean disabled;

	public StaffSearchRequest toStaffSearchRequest() {
		StaffSearchRequest request = new StaffSearchRequest();
		request.setTeamId(getTeamId());
		request.setEmploymentType(getEmploymentType());
		request.setEnteredDate(getEnteredDate());
		request.setTelework(isTelework());
		request.setDisabled(isDisabled());
		return request;
	}
}
