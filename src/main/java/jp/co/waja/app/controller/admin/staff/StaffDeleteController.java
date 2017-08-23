package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.exception.NotFoundException;
import jp.co.waja.exception.WrongDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
			@Validated @ModelAttribute(FORM_MODEL_KEY) StaffSearchForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/describe/{id}?error";
		}

		Optional<String> nameOptional = Optional.empty();
		try {
			nameOptional = staffService.delete(id);
		} catch (NotFoundException exception) {
			errors.reject("StaffNotFound");
		} catch (WrongDeleteException exception) {
			errors.reject("RelationalDataExist");
		}

		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/describe/{id}?error";
		}

		redirectAttributes.getFlashAttributes().clear();

		nameOptional.ifPresent(name -> redirectAttributes.addFlashAttribute("deletedName", name));
		return "redirect:/admin/staffs";
	}
}
