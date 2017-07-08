package jp.co.waja.core.web.controller.admin.worktime;

import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/work-time")
public class AdminWorkTimeController {

	@GetMapping("/list/{staffId}")
	public String list(
			@PathVariable(required = false) Long staffId,
			@RequestParam(required = false) LocalDate target,
			Model model) {
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		return "admin/worktime/list";
	}

	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		return "admin/worktime/edit";
	}

	@PostMapping("/edit")
	public String edit() {
		return "redirect:/admin/work-time/list";
	}
}
