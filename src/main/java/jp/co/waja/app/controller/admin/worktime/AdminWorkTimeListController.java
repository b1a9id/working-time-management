package jp.co.waja.app.controller.admin.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.*;

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
		Staff staff = staffService.findOneById(id);

		WorkTimeYearMonth workTimeYearMonth = workTimeService.getWorkTimeYearMonth(staff, yearMonth);
		if (Objects.isNull(workTimeYearMonth)) {
			workTimeYearMonth = workTimeService.createWorkTimeYearMonth(staff, yearMonth);
		}

		model.addAttribute("workTimeYearMonth", workTimeYearMonth);
		//TODO:フロントでやる
		model.addAttribute("workTimeSum", WorkTimeUtils.getWorkTimeSum(workTimeYearMonth.getWorkTimes()));
		return "admin/worktime/list";
	}
}
