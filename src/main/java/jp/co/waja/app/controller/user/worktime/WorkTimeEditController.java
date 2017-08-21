package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.entity.WorkTime;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/work-time/edit")
public class WorkTimeEditController {

	private static final String TARGET_ENTITY_KEY = "workTime";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private WorkTimeService workTimeService;

	@GetMapping
	public String edit(@AuthenticationPrincipal StaffDetails loginUser, Model model) {
		List<WorkTime> workTimes = workTimeService.getWorkTimes(loginUser.getStaff());

		model.addAttribute("workTimes", workTimes);
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		return "user/worktime/edit";
	}

	@PostMapping
	public String edit() {
		return "redirect:/work-time/list";
	}
}
