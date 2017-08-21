package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/work-time")
public class WorkTimeListController {

	@Autowired
	private WorkTimeService workTimeService;

	@GetMapping
	public String list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate displayDate,
			Model model) {
		displayDate = Optional.ofNullable(displayDate).orElse(LocalDate.now());
		List<WorkTime> workTimes = workTimeService.getWorkTimes(loginUser.getStaff(), displayDate);

		model.addAttribute("displayDate", displayDate);
		model.addAttribute("workTimes", workTimes);
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		//TODO:フロントでやる
		model.addAttribute("workTimeSum", WorkTimeUtils.getWorkTimeSum(workTimes));
		return "user/worktime/list";
	}
}
