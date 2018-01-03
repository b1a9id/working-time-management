package jp.co.waja.app.controller.admin.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin/work-time")
public class AdminWorkTimeListController {

	@Autowired
	private WorkTimeService workTimeService;

	@Autowired
	private StaffService staffService;

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		WorkTime.WorkType[] workTypes = WorkTime.WorkType.values();
		return Arrays.asList(workTypes);
	}

	@GetMapping("/{id}")
	public String list(
			@PathVariable Long id,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			Model model) {
		displayYearMonth = Optional.ofNullable(displayYearMonth).orElse(YearMonth.now());
		int yearMonth = WorkTimeUtil.yearMonthToInt(displayYearMonth);
		Staff staff = staffService.getStaff(id);

		WorkTimeYearMonth workTimeYearMonth = workTimeService.getWorkTimeYearMonth(staff, yearMonth);
		if (Objects.isNull(workTimeYearMonth)) {
			workTimeYearMonth = workTimeService.createWorkTimeYearMonth(staff, yearMonth);
		}

		model.addAttribute("workTimeYearMonth", workTimeYearMonth);
		model.addAttribute("businessDays", workTimeService.getBusinessDays(WorkTimeUtil.intToYearMonth(workTimeYearMonth.getWorkYearMonth())));
		//TODO:フロントでやる
		BigDecimal workTimeSum = WorkTimeUtils.getWorkTimeSum(workTimeYearMonth.getWorkTimes());
		BigDecimal paidVacationTimeSum = WorkTimeUtils.getPaidVacationTimes(workTimeYearMonth.getWorkTimes());
		model.addAttribute("workTimeSum", workTimeSum);
		model.addAttribute("totalWorkTime", workTimeSum.add(paidVacationTimeSum));
		return "admin/worktime/list";
	}
}
