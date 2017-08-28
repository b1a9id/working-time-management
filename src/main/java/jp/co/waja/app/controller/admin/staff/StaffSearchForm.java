package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class StaffSearchForm implements Serializable {

	private Long teamId;

	private WorkTime.WorkType workType;

	private LocalDate enteredDate;

	private boolean telework;

	public StaffSearchRequest toStaffSearchRequest() {
		return new StaffSearchRequest(getTeamId());
	}
}
