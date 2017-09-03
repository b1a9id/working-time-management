package jp.co.waja.app.controller.user.profile;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	private static final String TARGET_ENTITY_KEY = "staff";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/describe")
	public String describe() {
		return "user/profile/describe";
	}

	@GetMapping("/password/edit")
	public String passwordEdit(Model model) {
		PasswordEditForm form = (PasswordEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new PasswordEditForm());
		model.addAttribute(FORM_MODEL_KEY, form);
		return "user/profile/password/edit";
	}

	@PostMapping("/password/edit")
	public String passwordEdit(
			@AuthenticationPrincipal StaffDetails loginUser,
			@ModelAttribute(FORM_MODEL_KEY) @Validated PasswordEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/profile/password/edit?error";
		}
		Staff staff = loginUser.getStaff();

		if (!passwordEncoder.matches(form.getOldPassword(), staff.getPassword())) {
			errors.rejectValue("oldPassword", "valid.oldpassword");
		}
		if (!form.getNewPassword().equals(form.getRetypePassword())) {
			errors.rejectValue("retypePassword", "valid.retypepassword");
		}
		if (errors.hasErrors()) {
			return "redirect:/profile/password/edit?error";
		}

		Staff passwordUpdatedStaff = staffService.editPassword(staff.getId(), form.toPasswordEditRequest());
		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("passwordUpdatedStaff", passwordUpdatedStaff);
		return "redirect:/profile/describe";
	}
}
