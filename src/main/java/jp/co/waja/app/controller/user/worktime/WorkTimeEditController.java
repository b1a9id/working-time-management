package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work-time/edit")
public class WorkTimeEditController {

	private static final String TARGET_ENTITY_KEY = "workTimes";

	private static final String FORM_MODEL_KEY = "forms";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public List<WorkTime> setUpWorkTime(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate displayDate) {
		return workTimeService.getWorkTimes(loginUser.getStaff(), displayDate);
	}

	@ModelAttribute("workTypes")
	public List<WorkTime.WorkType> setUpWorkTypes() {
		return Arrays.asList(WorkTime.WorkType.values());
	}

	@GetMapping("/{displayDate}")
	public String edit(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate displayDate,
			Model model) {
		List<WorkTime> workTimes = (List<WorkTime>) model.asMap().get(TARGET_ENTITY_KEY);

		List<WorkTimeEditForm> workTimeEditForms = (List<WorkTimeEditForm>) model.asMap().get(FORM_MODEL_KEY);
		List<WorkTimeEditForm> forms = workTimes.stream()
				.map(WorkTimeEditForm::new)
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(workTimeEditForms)) {
			workTimeEditForms = forms;
		} else {
			Map<Long, WorkTimeEditForm> workTimeEditFormMap = new HashMap<>();
			forms.forEach(form -> workTimeEditFormMap.put(form.getId(), form));
			workTimeEditForms = workTimeEditForms.stream()
					.filter(workTimeEditForm -> Objects.nonNull(workTimeEditFormMap.get(workTimeEditForm.getId())))
					.peek(workTimeEditForm -> {
						WorkTimeEditForm newForm = workTimeEditFormMap.get(workTimeEditForm.getId());
						workTimeEditForm.setWorkType(newForm.getWorkType());
						workTimeEditForm.setStartAtHour(newForm.getStartAtHour());
						workTimeEditForm.setStartAtMinute(newForm.getStartAtMinute());
						workTimeEditForm.setEndAtHour(newForm.getEndAtHour());
						workTimeEditForm.setEndAtMinute(newForm.getEndAtMinute());
						workTimeEditForm.setRestTime(newForm.getRestTime());
						workTimeEditForm.setRemarks(newForm.getRemarks());
					}).collect(Collectors.toList());
		}

		Map<Long, WorkTime> workTimeMap = new HashMap<>();
		workTimes.forEach(workTime -> workTimeMap.put(workTime.getId(), workTime));

		model.addAttribute(FORM_MODEL_KEY, workTimeEditForms);
		model.addAttribute(TARGET_ENTITY_KEY, workTimeMap);
		model.addAttribute("displayDate", displayDate);
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		model.addAttribute("restTimes", WorkTimeUtil.restTime());
		return "user/worktime/edit";
	}

	@PostMapping("/{displayDate}")
	public String edit(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate displayDate,
			@ModelAttribute(FORM_MODEL_KEY) ArrayList<WorkTimeEditForm> forms,
			HttpServletRequest request,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "redirect:/work-time/edit/{displayDate}?error";
		}
		return "redirect:/work-time";
	}
}
