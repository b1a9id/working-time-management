package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/work-time/bulk-edit")
public class WorkTimeBulkEditController {

	private static final String TARGET_ENTITY_KEY = "workTime";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@PostMapping("/{yearMonth}")
	public String edit(
			@PathParam("yearMonth") String yearMonth,
			WorkTimeBulkEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return "redirect:/work-time?errors";
		}
		int updateQty = workTimeService.edit(yearMonth, form.toWorkTimeBulkEditRequest());

		redirectAttributes.addAttribute("updateQty", updateQty);
		return "redirect:/work-time";
	}
}
