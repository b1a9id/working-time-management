package jp.co.waja.app.controller.user.profile;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
public class ProfileRestController {

	@GetMapping("/profile/describe-staff")
	public Profile describe(@AuthenticationPrincipal StaffDetails staffDetails) {
		Staff staff = staffDetails.getStaff();
		Profile profile = new Profile();
		profile.setTeamName(staff.getTeam().getName());
		profile.setName(staff.getName());
		profile.setNameKana(staff.getNameKana());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		profile.setEnteredDate(formatter.format(staff.getEnteredDate()));
		return profile;
	}
}
