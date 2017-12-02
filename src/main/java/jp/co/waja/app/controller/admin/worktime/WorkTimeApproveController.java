package jp.co.waja.app.controller.admin.worktime;

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
@RequestMapping("/work-time/approve")
public class WorkTimeApproveController {

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping(value = "/{id}", params = "approve1")
	public String approve1(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id,
			@RequestParam Boolean approve1,
			RedirectAttributes redirectAttributes) {
		WorkTimeYearMonth updatedWorkTimeYearMonth = workTimeService.approve1(loginUser.getStaff(), id, approve1);

		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		redirectAttributes.addAttribute("id", updatedWorkTimeYearMonth.getStaff().getId());
		return "redirect:/admin/work-time/{id}";
	}

	@PostMapping(value = "/{id}", params = "approve2")
	public String approve2(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id,
			@RequestParam Boolean approve2,
			RedirectAttributes redirectAttributes) {
		WorkTimeYearMonth updatedWorkTimeYearMonth = workTimeService.approve2(loginUser.getStaff(), id, approve2);

		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		redirectAttributes.addAttribute("id", updatedWorkTimeYearMonth.getStaff().getId());
		return "redirect:/admin/work-time/{id}";
	}
}
