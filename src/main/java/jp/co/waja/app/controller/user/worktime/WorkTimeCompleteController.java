package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/work-time/complete")
public class WorkTimeCompleteController {

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{displayYearMonth}")
	public String complete(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable String displayYearMonth,
			@RequestParam Boolean complete,
			RedirectAttributes redirectAttributes) {
		if (!complete) {
			return "redirect:/work-time?error";
		}
		WorkTimeYearMonth updatedWorkTimeYearMonth =
				workTimeService.complete(loginUser.getStaff(), displayYearMonth, complete);
		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		return "redirect:/work-time";
	}
}
