package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/teams/edit/{id}")
public class TeamEditController {

	private static final String TARGET_ENTITY_KEY = "team";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public Team setupTeam(@PathVariable Long id) {
		return teamService.getTeam(id);
	}

	@GetMapping
	public String edit(Model model) {
		Team team = (Team) model.asMap().get(TARGET_ENTITY_KEY);
		TeamEditForm form = (TeamEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new TeamEditForm(team));

		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute(TARGET_ENTITY_KEY, team);

		return "admin/team/edit";
	}

	@PostMapping
	public String edit(
			@PathVariable Long id,
			@Validated @ModelAttribute(FORM_MODEL_KEY) TeamEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/teams/edit?error";
		}

		Team updateTeam = teamService.edit(form.toTeamEditRequest(), id);
		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addAttribute("id", updateTeam.getId());
		redirectAttributes.addFlashAttribute("updateTeam", updateTeam);

		return "redirect:/admin/teams";
	}
}
