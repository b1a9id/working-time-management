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

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/work-time")
public class WorkTimeListController {

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		return Arrays.asList(WorkTime.WorkType.values());
	}

	@GetMapping
	public String list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			Model model) {
		displayYearMonth = Optional.ofNullable(displayYearMonth).orElse(YearMonth.now());
		int yearMonth = WorkTimeUtil.yearMonthToInt(displayYearMonth);
		WorkTimeYearMonth workTimeYearMonth = workTimeService.getWorkTimeYearMonth(loginUser.getStaff(), yearMonth);
		if (isNull(workTimeYearMonth)) {
			workTimeYearMonth = workTimeService.createWorkTimeYearMonth(loginUser.getStaff(), yearMonth);
		}

		model.addAttribute("workTimeYearMonth", workTimeYearMonth);
		model.addAttribute("businessDays", workTimeService.getBusinessDays(WorkTimeUtil.intToYearMonth(workTimeYearMonth.getWorkYearMonth())));
		//TODO:フロントでやる
		BigDecimal workTimeSum = WorkTimeUtils.getWorkTimeSum(workTimeYearMonth.getWorkTimes());
		BigDecimal paidVacationTimeSum = WorkTimeUtils.getPaidVacationTimes(workTimeYearMonth.getWorkTimes());
		model.addAttribute("workTimeSum", workTimeSum);
		model.addAttribute("totalWorkTime", workTimeSum.add(paidVacationTimeSum));
		return "user/worktime/list";
	}
}
