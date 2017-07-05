package jp.co.waja.core.web.controller.admin.worktime;

import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/work-time")
public class AdminWorkTimeController {

	@GetMapping("/list")
	public String list() {
		return "admin/worktime/list";
	}

	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("timePerOne", WorkTimeUtil.timePerOne());
		return "admin/worktime/edit";
	}

	@PostMapping("/edit")
	public String edit() {
		return "redirect:/admin/work-time/list";
	}
}
