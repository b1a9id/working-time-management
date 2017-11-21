package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin/teams")
public class TeamListController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String list(Model model) {
		List<Team> teams = teamService.getTeams();
		List<Staff> staffs = staffService.getAllByAdminAndManager();
		Map<Team, String> managers = new HashMap<>();
		staffs.forEach(staff -> managers.put(staff.getTeam(), staff.getName()));

		model.addAttribute("teams", teams);
		model.addAttribute("managers", managers);
		return "admin/team/list";
	}
}
