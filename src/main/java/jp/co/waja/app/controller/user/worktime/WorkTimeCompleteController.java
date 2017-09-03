package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/work-time/complete")
public class WorkTimeCompleteController {

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{id}")
	public String complete(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id,
			@RequestParam Boolean complete,
			RedirectAttributes redirectAttributes) {
		WorkTimeYearMonth workTimeYearMonth = workTimeService.getWorkTimeYearMonth(id);
		if (workTimeYearMonth.getCompletedAt() != null) {
			redirectAttributes.addFlashAttribute("notModify", "notModify");
			return "redirect:/work-time?error";
		}
		WorkTimeYearMonth completeWorkTimeYearMonth =
				workTimeService.complete(loginUser.getStaff(), id, complete);
		redirectAttributes.addFlashAttribute("completeWorkTimeYearMonth", completeWorkTimeYearMonth);
		return "redirect:/work-time/";
	}
}
