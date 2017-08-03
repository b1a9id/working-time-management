package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.domain.service.staff.StaffService;
import jp.co.waja.domain.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/staffs/edit")
public class StaffEditController {

	private static final String TARGET_ENTITY_KEY = "staff";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@Autowired
	private StaffService staffService;

	@ModelAttribute(TARGET_ENTITY_KEY)
	public Staff setupStaff(@RequestParam Long id) {
		return staffService.findOneById(id);
	}

	@ModelAttribute("teams")
	public List<Team> setUpTeam() {
		return teamService.teams();
	}

	@ModelAttribute("genders")
	public List<Staff.Gender> setUpGender() {
		return Arrays.asList(Staff.Gender.values());
	}

	@ModelAttribute("employmentTypes")
	public List<Staff.EmploymentType> setUpEmploymentType() {
		return Arrays.asList(Staff.EmploymentType.values());
	}

	@GetMapping
	public String edit(Model model) {
		Staff staff = (Staff) model.asMap().get(TARGET_ENTITY_KEY);
		StaffEditForm form = (StaffEditForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new StaffEditForm(staff));

		model.addAttribute(FORM_MODEL_KEY, form);
		model.addAttribute(TARGET_ENTITY_KEY, staff);

		return "admin/staff/edit";
	}

	@PostMapping
	public String edit(
			@Validated @ModelAttribute(FORM_MODEL_KEY) StaffEditForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/edit?error";
		}

		Staff savedStaff = staffService.edit(form.toStaffEditRequest());
		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addAttribute("id", savedStaff.getId());
		redirectAttributes.addFlashAttribute("savedStaff", savedStaff);
		redirectAttributes.addFlashAttribute("staffId", savedStaff.getId());

		return "redirect:/admin/staffs/describe";
	}
}
