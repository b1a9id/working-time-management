package jp.co.waja.app.controller.admin.worktime;

import jp.co.waja.app.controller.user.worktime.WorkTimeYearMonthEditForm;
import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.model.worktime.WorkTimeYearMonthEditRequest;
import jp.co.waja.core.service.staff.*;
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
import java.util.*;

@Controller
@RequestMapping("/admin/work-time/edit")
public class AdminWorkTimeEditController {

	private static final String TARGET_ENTITY_KEY = "workTimeYearMonth";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@Autowired
	private StaffService staffService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public WorkTimeYearMonth setUpWorkTime(
			@PathVariable Long id,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth) {
		Staff staff = staffService.getStaff(id);
		return workTimeService.getWorkTimeYearMonth(staff, WorkTimeUtil.yearMonthToInt(displayYearMonth));
	}

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		return Arrays.asList(WorkTime.WorkType.values());
	}

	@GetMapping("/{id}")
	public String edit(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			Model model) {
		WorkTimeYearMonth workTimeYearMonth = (WorkTimeYearMonth) model.asMap().get(TARGET_ENTITY_KEY);
		WorkTimeYearMonthEditForm form = (WorkTimeYearMonthEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new WorkTimeYearMonthEditForm(workTimeYearMonth));

		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute(TARGET_ENTITY_KEY, workTimeYearMonth);
		model.addAttribute("restTimes", WorkTimeUtil.restTime());
		return "admin/worktime/edit";
	}

	@PostMapping("/{id}")
	public String edit(
			@PathVariable Long id,
			@AuthenticationPrincipal StaffDetails loginUser,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth displayYearMonth,
			@ModelAttribute(FORM_MODEL_KEY) WorkTimeYearMonthEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "redirect:/admin/work-time/edit/{displayDate}?error";
		}
		WorkTimeYearMonthEditRequest request = form.toWorkTimeYearMonthEditRequest();
		Staff staff = loginUser.getStaff();
		if (loginUser.getStaff().getRole() == Role.ADMIN) {
			staff = staffService.getStaff(id);
		}
		WorkTimeYearMonth updatedWorkTimeYearMonth = workTimeService.edit(staff, request);

		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		return "redirect:/admin/work-time/{id}";
	}
}
