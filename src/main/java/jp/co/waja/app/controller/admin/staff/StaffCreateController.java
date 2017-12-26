package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.exception.DuplicatedException;
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
		return teamService.getTeams();
	}

	@ModelAttribute("genders")
	public List<Staff.Gender> setUpGender() {
		return Arrays.asList(Staff.Gender.values());
	}

	@ModelAttribute("employmentTypes")
	public List<Staff.EmploymentType> setUpEmploymentType() {
		return Arrays.asList(Staff.EmploymentType.PERMANENT_STAFF);
	}

	@ModelAttribute("roles")
	public List<Role> setUpRoles() {
		return Arrays.asList(Role.ADMIN, Role.MANAGER, Role.STAFF);
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

		if (form.getEmploymentType() != Staff.EmploymentType.PERMANENT_STAFF) {
			if (form.getFlextime()) {
				errors.rejectValue("flextime", "error.cannot.flextime");
			}
			if (form.getTelework()) {
				errors.rejectValue("telework", "error.cannot.telework");
			}
			if (form.getRole() != Role.CREW) {
				errors.rejectValue("role", "error.cannot.choice.role");
			}
		}

		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/create?error";
		}

		Staff savedStaff = null;
		try {
			savedStaff = staffService.create(form.toStaffCreateRequest());
		} catch (DuplicatedException e) {
			errors.rejectValue("code", "duplicated");
		}

		if (errors.hasErrors()) {
			return "redirect:/admin/staffs/create?error";
		}

		redirectAttributes.addAttribute("id", savedStaff.getId());
		redirectAttributes.addFlashAttribute("savedStaff", savedStaff);
		redirectAttributes.addFlashAttribute("staffId", savedStaff.getId());

		return "redirect:/admin/staffs/describe/{id}";
	}
}
