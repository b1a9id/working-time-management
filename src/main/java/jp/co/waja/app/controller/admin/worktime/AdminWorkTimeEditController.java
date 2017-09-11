package jp.co.waja.app.controller.admin.worktime;

import jp.co.waja.app.controller.user.worktime.WorkTimeYearMonthEditForm;
import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.worktime.WorkTimeYearMonthEditRequest;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/work-time/edit")
public class AdminWorkTimeEditController {

	private static final String TARGET_ENTITY_KEY = "workTimeYearMonth";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public WorkTimeYearMonth setUpWorkTime(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth) {
		return workTimeService.getWorkTimeYearMonth(loginUser.getStaff(), WorkTimeUtil.yearMonthToInt(displayYearMonth));
	}

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		return Arrays.asList(WorkTime.WorkType.values());
	}

	@GetMapping("/{displayYearMonth}")
	public String edit(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			Model model) {
		WorkTimeYearMonth workTimeYearMonth = (WorkTimeYearMonth) model.asMap().get(TARGET_ENTITY_KEY);
		WorkTimeYearMonthEditForm form = (WorkTimeYearMonthEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new WorkTimeYearMonthEditForm(workTimeYearMonth));

		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute(TARGET_ENTITY_KEY, workTimeYearMonth);
		model.addAttribute("restTimes", WorkTimeUtil.restTime());
		return "admin/worktime/edit";
	}

	@PostMapping("/{displayYearMonth}")
	public String edit(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			@ModelAttribute(FORM_MODEL_KEY) WorkTimeYearMonthEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "redirect:/work-time/edit/{displayDate}?error";
		}
		WorkTimeYearMonthEditRequest request = form.toWorkTimeYearMonthEditRequest();
		WorkTimeYearMonth updatedWorkTimeYearMonth = workTimeService.edit(loginUser.getStaff(), request);

		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		return "redirect:/admin/work-time";
	}
}
