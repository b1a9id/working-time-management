package jp.co.waja.app.controller.user.worktime;

import jp.co.waja.core.support.WorkTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work-time")
public class WorkTimeController {

//	@GetMapping("/list")
//	public String list(Model model) {
//		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
//		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
//		return "user/worktime/list";
//	}

	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("workTimeHour", WorkTimeUtil.workTimeHour());
		model.addAttribute("workTimeMinute", WorkTimeUtil.workTimeMinute());
		return "user/worktime/edit";
	}

	@PostMapping("/edit")
	public String edit() {
		return "redirect:/work-time/list";
	}
}
