package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.domain.service.staff.StaffService;
import jp.co.waja.domain.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/staffs")
public class StaffCreateController {

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private TeamService teamService;

	@Autowired
	private StaffService staffService;

	@ModelAttribute(FORM_MODEL_KEY)
	public StaffCreateForm setUpStaffCreateForm() {
		return new StaffCreateForm();
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

	@GetMapping("/create")
	public String create() {
		return "admin/staff/create";
	}

	@PostMapping("/create")
	public String create(
			@Validated @ModelAttribute(FORM_MODEL_KEY) StaffCreateForm form,
			BindingResult errors,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		redirectAttributes.addFlashAttribute(ERRORS_MODEL_KEYS, errors);
		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/create?error";
		}

		Staff savedStaff = staffService.create(form.toStaffCreateRequest());
		redirectAttributes.addAttribute("id", savedStaff.getId());
		redirectAttributes.addFlashAttribute("savedStaff", savedStaff);
		redirectAttributes.addFlashAttribute("staffId", savedStaff.getId());

		return "redirect:/admin/staffs/describe";
	}
}
