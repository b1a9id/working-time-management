package jp.co.waja.core.web.controller.user.worktime;

import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/work-time")
public class WorkTimeController {

	@GetMapping("/list")
	public String list() {
		return "user/worktime/list";
	}

	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("timePerOne", WorkTimeUtil.timePerOne());
		return "user/worktime/edit";
	}

	@PostMapping("/edit")
	public String edit() {
		return "redirect:/work-time/list";
	}
}
