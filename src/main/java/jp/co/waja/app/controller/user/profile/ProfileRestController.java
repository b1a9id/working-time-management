package jp.co.waja.app.controller.user.profile;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileRestController {

	@GetMapping("/profile/describe-staff")
	public Profile describe(@AuthenticationPrincipal StaffDetails staffDetails) {
		Staff staff = staffDetails.getStaff();
		Profile profile = new Profile();
		profile.setTeamName(staff.getTeam().getName());
		profile.setName(staff.getName());
		profile.setNameKana(staff.getNameKana());
		profile.setEnteredDate(staff.getEnteredDate().toString());
		return profile;
	}
}
