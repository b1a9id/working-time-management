package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.model.staff.StaffSearchRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StaffSearchForm implements Serializable {

	private Long id;

	private Long teamId;

	public StaffSearchRequest toStaffSearchRequest() {
		return new StaffSearchRequest(getId(), getTeamId());
	}
}
