package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;

@RestController
@RequestMapping("/work-time/bulk-edit")
public class WorkTimeBulkEditController {

	private static final String TARGET_ENTITY_KEY = "workTime";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{today}")
	public String edit(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable String today,
			@RequestBody @Validated WorkTimeBulkEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/work-time?errors";
		}

		LocalTime startAt = LocalTime.of(form.getStartAtHour(), form.getStartAtMinute());
		LocalTime endAt = LocalTime.of(form.getEndAtHour(), form.getEndAtMinute());
		// TODO:開始時間の方が遅い時エラー

		int updateQty = workTimeService.edit(loginUser.getStaff(), today, form.toWorkTimeBulkEditRequest(startAt, endAt));

		redirectAttributes.addAttribute("updateQty", updateQty);
		return "redirect:/work-time";
	}
}
