package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.domain.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/staffs/delete")
public class StaffDeleteController {

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@DeleteMapping
	public String delete(
			@Validated @ModelAttribute(FORM_MODEL_KEY) StaffSearchForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/staffs";
		}

		Staff savedStaff = staffService.findOneById(form.toStaffSearchRequest().getId());
		if (savedStaff == null) {
			errors.reject("staffNotFound");
			return "redirect:/admin/staffs";
		}

		staffService.delete(savedStaff);
		redirectAttributes.addFlashAttribute("savedStaff", savedStaff.getName());
		return "redirect:/admin/teams";
	}
}
