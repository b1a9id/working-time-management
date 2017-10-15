package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin/staffs/password")
public class PasswordEditController {

	private static final String TARGET_ENTITY_KEY = "staff";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@GetMapping("/edit/{id}")
	public String passwordEdit(@PathVariable Long id, Model model) {
		Staff staff = staffService.getStaff(id);
		if (Objects.isNull(staff)) {
			return "redirect:/admin/staffs";
		}
		PasswordInitForm form = (PasswordInitForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new PasswordInitForm());
		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute("staff" ,staff);
		return "admin/staff/password/edit";
	}

	@PostMapping("/edit/{id}")
	public String passwordEdit(
			@PathVariable Long id,
			@ModelAttribute(FORM_MODEL_KEY) @Validated PasswordInitForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/password/edit/{id}?error";
		}

		Staff staff = staffService.getStaff(id);
		if (Objects.isNull(staff)) {
			return "redirect:/admin/staffs";
		}

		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/password/edit/{id}?error";
		}

		Staff passwordUpdatedStaff = staffService.initPassword(staff.getId(), form.toPasswordEditRequest());
		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("passwordUpdatedStaff", passwordUpdatedStaff);
		return "redirect:/admin/staffs";
	}
}
