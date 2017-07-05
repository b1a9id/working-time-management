package jp.co.waja.core.web.controller.admin.team;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/team")
public class AdminTeamController {

	@GetMapping("/list")
	public String list() {
		return "admin/team/list";
	}

	@GetMapping("/create")
	public String create() {
		return "admin/team/create";
	}

	@GetMapping("/describe/{teamId}")
	public String describe(@PathVariable Long teamId) {
		return "admin/team/describe";
	}

	@GetMapping("/edit/{teamId}")
	public String edit(@PathVariable Long teamId) {
		return "admin/team/edit";
	}
}
