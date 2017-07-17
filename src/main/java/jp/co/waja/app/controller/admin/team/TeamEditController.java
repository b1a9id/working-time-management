package jp.co.waja.app.controller.admin.team;

import jp.co.waja.domain.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/teams/edit")
public class TeamEditController {

	@Autowired
	private TeamService teamService;

	@GetMapping("/{teamId}")
	public String edit(
			@PathVariable Long teamId,
			Model model) {
		return "admin/team/edit";
	}

	@PostMapping("/{teamId}")
	public String edit(@PathVariable Long teamId) {
		return "redirect:/admin/team/list";
	}
}
