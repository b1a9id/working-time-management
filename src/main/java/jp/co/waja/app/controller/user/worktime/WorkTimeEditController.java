package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/work-time/edit")
public class WorkTimeEditController {

	private static final String TARGET_ENTITY_KEY = "workTimeYearMonth";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public WorkTimeYearMonth setUpWorkTime(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id) {
		return workTimeService.getWorkTimeYearMonth(loginUser.getStaff(), id);
	}

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes(@AuthenticationPrincipal StaffDetails loginUser) {
		return WorkTimeUtil.workTypes(loginUser.getStaff());
	}

	@GetMapping("/{id}")
	public String edit(
			@PathVariable Long id,
			Model model) {
		WorkTimeYearMonth workTimeYearMonth = (WorkTimeYearMonth) model.asMap().get(TARGET_ENTITY_KEY);
		WorkTimeYearMonthEditForm form = (WorkTimeYearMonthEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new WorkTimeYearMonthEditForm(workTimeYearMonth));

		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute(TARGET_ENTITY_KEY, workTimeYearMonth);
		model.addAttribute("restTimes", WorkTimeUtil.restTime());
		return "user/worktime/edit";
	}

	@PostMapping("/{id}")
	public String edit(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id,
			@ModelAttribute(FORM_MODEL_KEY) WorkTimeYearMonthEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "redirect:/work-time/edit/{displayDate}?error";
		}
		WorkTimeYearMonth updatedWorkTimeYearMonth = workTimeService.edit(loginUser.getStaff(), form.toWorkTimeYearMonthEditRequest());

		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("updatedWorkTimeYearMonth", updatedWorkTimeYearMonth);
		redirectAttributes.addAttribute("yearMonth", WorkTimeUtil.intToYearMonth(updatedWorkTimeYearMonth.getWorkYearMonth()));
		return "redirect:/work-time?displayYearMonth={yearMonth}";
	}
}
