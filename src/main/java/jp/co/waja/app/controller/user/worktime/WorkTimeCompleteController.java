package jp.co.waja.app.controller.user.worktime;

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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth parsedDisplayYearMonth = YearMonth.parse(displayYearMonth, formatter);
		WorkTimeYearMonth workTimeYearMonth =
				workTimeService.getWorkTimeYearMonth(loginUser.getStaff(), WorkTimeUtil.yearMonthToInt(parsedDisplayYearMonth));
		if (workTimeYearMonth.isComplete()) {
			redirectAttributes.addFlashAttribute("notModify", "notModify");
			return "redirect:/work-time?error";
		}
		WorkTimeYearMonth updatedWorkTimeYearMonth =
				workTimeService.complete(loginUser.getStaff(), displayYearMonth, complete);
		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		return "redirect:/work-time";
	}
}
