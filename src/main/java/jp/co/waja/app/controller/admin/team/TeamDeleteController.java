package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/teams/delete")
public class TeamDeleteController {

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@DeleteMapping
	public String delete(
			@Validated @ModelAttribute(FORM_MODEL_KEY) TeamSearchForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/teams";
		}

		Team savedTeam = teamService.findOneById(form.toTeamSearchRequest().getTeamId());
		if (savedTeam == null) {
			errors.reject("teamNotFound");
			return "redirect:/admin/teams";
		}

		teamService.delete(savedTeam.getId());
		redirectAttributes.addFlashAttribute("deletedName", savedTeam.getName());
		return "redirect:/admin/teams";
	}
}
