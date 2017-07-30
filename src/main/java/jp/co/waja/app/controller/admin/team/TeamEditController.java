package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.domain.service.team.TeamService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/teams/edit")
public class TeamEditController {

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@ModelAttribute(FORM_MODEL_KEY)
	public TeamEditForm setUpTeamEditForm() {
		return new TeamEditForm();
	}

	@GetMapping()
	public String edit(
			@ModelAttribute(FORM_MODEL_KEY) TeamEditForm form,
			Model model,
			BindingResult errors) {
		Team savedTeam = teamService.findOneById(form.getId());
		if (savedTeam == null) {
			errors.reject("teamNotFound");
			return "redirect:/admin/teams";
		}

		form.setName(savedTeam.getName());
		form.setShortName(savedTeam.getShortName());
		model.addAttribute(FORM_MODEL_KEY, savedTeam);
		return "admin/team/edit";
	}

	@PostMapping()
	public String edit(
			@Validated @ModelAttribute(FORM_MODEL_KEY) TeamEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/teams/create?error";
		}

		Team updateTeam = null;
		try {
			updateTeam = teamService.edit(form.toTeamEditRequest());
		} catch (NotFoundException e) {
			errors.reject("teamNotFound");
		}

		if (updateTeam == null || errors.hasErrors()) {
			return "redirect:/admin/teams/create?error";
		}

		redirectAttributes.addFlashAttribute("updateTeam", updateTeam);
		return "redirect:/admin/teams";
	}
}
