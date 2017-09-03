package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/teams/delete/{id}")
public class TeamDeleteController {

	@Autowired
	private TeamService teamService;

	@DeleteMapping
	public String delete(
			@PathVariable Long id,
			RedirectAttributes redirectAttributes) {
		Optional<String> nameOptional = Optional.empty();
		try {
			nameOptional = teamService.delete(id);
		} catch (NotFoundException exception) {
			return "redirect:/admin/teams";
		} catch (WrongDeleteException exception) {
			Optional<String> cause = Optional.empty();
			if ("StaffExist".equals(exception.getMessage())) {
				cause = Optional.of("スタッフが存在する");
			}
			cause.ifPresent(c -> redirectAttributes.addFlashAttribute("wrongDelete", c));
		}

		if (!redirectAttributes.getFlashAttributes().isEmpty()) {
			return "redirect:/admin/teams?error";
		}

		redirectAttributes.getFlashAttributes().clear();
		nameOptional.ifPresent(name -> redirectAttributes.addFlashAttribute("deletedName", name));
		return "redirect:/admin/teams";
	}
}
