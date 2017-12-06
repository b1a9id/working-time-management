package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.app.support.PageWrapper;
import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/staffs")
public class StaffListController {

	private static final String FORM_MODEL_KEY = "form";

	@Autowired
	private TeamService teamService;

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute(FORM_MODEL_KEY)
	public StaffSearchForm setUpStaffSearchForm() {
		return new StaffSearchForm();
	}

	@ModelAttribute("teams")
	public List<Team> setUpWorkTeams() {
		return teamService.getTeams();
	}

	@ModelAttribute("employmentTypes")
	public List<Staff.EmploymentType> setUpWorkTypes() {
		return Arrays.asList(Staff.EmploymentType.values());
	}

	@ModelAttribute("roles")
	public List<Role> setUpRoles() {
		return Arrays.asList(Role.ADMIN, Role.MANAGER, Role.STAFF);
	}

	@GetMapping
	public String list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PageableDefault(size = 25, sort = "id") Pageable pageable,
			Model model) {
		StaffSearchForm form = (StaffSearchForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new StaffSearchForm());

		Page<Staff> staffs = staffService.getStaffs(loginUser.getStaff(), form.toStaffSearchRequest(), pageable);

		Map<Long, Boolean> existWorkTimeMap = new HashMap<>();
		staffs.forEach(staff -> existWorkTimeMap.put(staff.getId(), workTimeService.countByStaff(staff) > 0));

		model.addAttribute("staffs", staffs);
		model.addAttribute("pagination", new PageWrapper<>(staffs));
		model.addAttribute("existWorkTimeMap", existWorkTimeMap);
		return "admin/staff/list";
	}

	@PostMapping
	public String list(
			@ModelAttribute(FORM_MODEL_KEY) StaffSearchForm form,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		return "redirect:/admin/staffs";
	}
}
