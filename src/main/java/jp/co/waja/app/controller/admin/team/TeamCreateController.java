package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.domain.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/teams/create")
public class TeamCreateController {

	public static final String FORM_MODEL_KEY = "form";

	public static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@ModelAttribute(FORM_MODEL_KEY)
	public TeamCreateForm setUpTeamCreateForm() {
		return new TeamCreateForm();
	}

	@GetMapping
	public String create() {
		return "admin/team/create";
	}

	@PostMapping
	public String create(
			@Validated @ModelAttribute(FORM_MODEL_KEY) TeamCreateForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/teams/create?error";
		}

		Team savedTeam = teamService.create(form.toTeamCreateRequest());
		if (savedTeam == null || errors.hasErrors()) {
			return "redirect:/admin/teams/create?error";
		}

		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("savedTeam", savedTeam);

		return "redirect:/admin/teams";
	}
}
