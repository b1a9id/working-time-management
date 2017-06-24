package jp.co.waja.core.web.controller.worktime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/work-time")
public class WorkTimeListController {

	@GetMapping("/list")
	public String list() {
		return "worktime/list";
	}
}
