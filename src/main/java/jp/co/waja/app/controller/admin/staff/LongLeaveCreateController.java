package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.longleave.LongLeaveService;
import jp.co.waja.core.service.staff.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/staffs/long-leave/create")
public class LongLeaveCreateController {

	private static final String TARGET_ENTITY_KEY = "staff";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@Autowired
	private LongLeaveService longLeaveService;

	@ModelAttribute("staffs")
	public List<Staff> setUpStaffs() {
		return staffService.getStaffsByEmploymentType(Staff.EmploymentType.PERMANENT_STAFF);
	}

	@ModelAttribute("types")
	public List<LongLeave.Type> setUpTypes() {
		return Arrays.asList(LongLeave.Type.values());
	}

	@GetMapping
	public String create(Model model) {
		LongLeaveCreateForm form = (LongLeaveCreateForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new LongLeaveCreateForm());

		if (form.getForms() == null) {
			List<LongLeaveForm> longLeaveForms = new ArrayList<>();
			form.setForms(longLeaveForms);
		}

		if (CollectionUtils.isEmpty(form.getForms())) {
			form.setForms(Collections.singletonList(new LongLeaveForm()));
		}

		model.addAttribute(FORM_MODEL_KEY, form);
		return "admin/staff/longleave/create";
	}

	@PostMapping
	public String create(
			@AuthenticationPrincipal StaffDetails loginUser,
			@Validated @ModelAttribute(FORM_MODEL_KEY) LongLeaveCreateForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);

		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/long-leave/create?error";
		}

		List<LongLeave> savedLongLeaves = longLeaveService.create(form.toLongLeaveCreateRequest());
		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("savedLongLeaves", savedLongLeaves);
		return "redirect:/admin/staffs";
	}
}
