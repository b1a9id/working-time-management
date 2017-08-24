package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/staffs/delete/{id}")
public class StaffDeleteController {

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@DeleteMapping
	public String delete(
			@PathVariable Long id,
			RedirectAttributes redirectAttributes) {
		Optional<String> nameOptional = Optional.empty();
		try {
			nameOptional = staffService.delete(id);
		} catch (NotFoundException exception) {
			return "redirect:/admin/staffs";
		} catch (WrongDeleteException exception) {
			Optional<String> cause = Optional.empty();
			if ("WorkTimeExist".equals(exception.getMessage())) {
				cause = Optional.of("稼働時間が存在する");
			}
			cause.ifPresent(c -> redirectAttributes.addFlashAttribute("wrongDelete", c));
		}

		if (!redirectAttributes.getFlashAttributes().isEmpty()) {
			return "redirect:/admin/staffs/describe/{id}?error";
		}

		redirectAttributes.getFlashAttributes().clear();
		nameOptional.ifPresent(name -> redirectAttributes.addFlashAttribute("deletedName", name));
		return "redirect:/admin/staffs";
	}
}
