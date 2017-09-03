package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/teams")
public class TeamListController {

	@Autowired
	private TeamService teamService;

	@GetMapping
	public String list(Model model) {
		List<Team> teams = teamService.getTeams();
		model.addAttribute("teams", teams);
		return "admin/team/list";
	}
}
