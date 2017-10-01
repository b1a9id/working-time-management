package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.*;

@Controller
@RequestMapping("/work-time")
public class WorkTimeListController {

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		WorkTime.WorkType[] workTypes = WorkTime.WorkType.values();
		return Arrays.asList(workTypes);
	}

	@GetMapping
	public String list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			Model model) {
		displayYearMonth = Optional.ofNullable(displayYearMonth).orElse(YearMonth.now());
		int yearMonth = WorkTimeUtil.yearMonthToInt(displayYearMonth);
		Staff staff = loginUser.getStaff();
		WorkTimeYearMonth workTimeYearMonth = workTimeService.getWorkTimeYearMonth(staff, yearMonth);
		if (Objects.isNull(workTimeYearMonth)) {
			workTimeYearMonth = workTimeService.createWorkTimeYearMonth(staff, yearMonth);
		}

		model.addAttribute("workTimeYearMonth", workTimeYearMonth);
		//TODO:フロントでやる
		model.addAttribute("workTimeSum", WorkTimeUtils.getWorkTimeSum(workTimeYearMonth.getWorkTimes()));
		return "user/worktime/list";
	}
}
