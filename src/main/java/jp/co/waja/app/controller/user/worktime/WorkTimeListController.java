package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/work-time")
public class WorkTimeListController {

	@Autowired
	private WorkTimeService workTimeService;

	@GetMapping
	public String list(@AuthenticationPrincipal StaffDetails loginUser, Model model) {
		List<WorkTime> workTimes = workTimeService.getWorkTimes(loginUser.getStaff());
		LocalDate today = LocalDate.now();

		model.addAttribute("today", today);
		model.addAttribute("workTimes", workTimes);
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		return "user/worktime/list";
	}
}
