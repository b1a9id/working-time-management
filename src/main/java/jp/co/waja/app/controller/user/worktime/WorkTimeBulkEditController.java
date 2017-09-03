package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.exception.ForbiddenException;
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

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{displayYearMonth}")
	public String edit(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable String displayYearMonth,
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
		if (startAt.isAfter(endAt)) {
			throw new ForbiddenException("startAt is not after endAt");
		}

		WorkTimeYearMonth workTimeYearMonth = workTimeService.bulkEdit(loginUser.getStaff(), displayYearMonth, form.toWorkTimeBulkEditRequest(startAt, endAt));
		return "redirect:/work-time";
	}
}
