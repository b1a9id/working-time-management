package jp.co.waja.app.controller.admin.team;

import jp.co.waja.domain.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/teams/delete")
public class TeamDeleteController {

	@Autowired
	private TeamService teamService;

	@DeleteMapping("/{teamId}")
	public String delete(
			@PathVariable Long teamId) {
		return "redirect:/admin/team/list";
	}
}
