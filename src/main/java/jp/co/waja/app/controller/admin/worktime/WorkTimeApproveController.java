package jp.co.waja.app.controller.admin.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/work-time/approve")
public class WorkTimeApproveController {

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{displayYearMonth}")
	public String approve(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable String displayYearMonth,
			@RequestParam Boolean approve1,
			RedirectAttributes redirectAttributes) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth parsedDisplayYearMonth = YearMonth.parse(displayYearMonth, formatter);
		WorkTimeYearMonth workTimeYearMonth =
				workTimeService.getWorkTimeYearMonth(loginUser.getStaff(), WorkTimeUtil.yearMonthToInt(parsedDisplayYearMonth));

		WorkTimeYearMonth updatedWorkTimeYearMonth;
		if (approve1) {
			updatedWorkTimeYearMonth = workTimeService.approve1(loginUser.getStaff(), displayYearMonth, approve1);
		} else {
			updatedWorkTimeYearMonth = workTimeService.complete(loginUser.getStaff(), displayYearMonth, approve1);
		}

		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		return "redirect:/work-time";
	}
}
