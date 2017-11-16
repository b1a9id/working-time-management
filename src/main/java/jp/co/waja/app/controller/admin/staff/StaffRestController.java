package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.app.support.PageWrapper;
import jp.co.waja.core.entity.Staff;
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
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RestController
@RequestMapping("/admin/staffs/rest")
public class StaffRestController {

	private static final String FORM_MODEL_KEY = "form";

	@Autowired
	private TeamService teamService;

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute("roles")
	public List<Role> setUpRoles() {
		return Arrays.asList(Role.values());
	}

	@GetMapping
	public PageWrapper list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PageableDefault(size = 25, sort = "id") Pageable pageable,
			Model model) {
		StaffSearchForm form = (StaffSearchForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new StaffSearchForm());

		Page<Staff> staffs = staffService.getStaffs(loginUser.getStaff(), form.toStaffSearchRequest(), pageable);

		Map<Long, Boolean> existWorkTimeMap = new HashMap<>();
		staffs.forEach(staff -> existWorkTimeMap.put(staff.getId(), workTimeService.countByStaff(staff) > 0));
		return new PageWrapper<>(staffs);
	}

	@PostMapping
	public PageWrapper list(
			@AuthenticationPrincipal StaffDetails loginUser,
			@RequestBody @Validated StaffSearchForm form,
			@PageableDefault(size = 25, sort = "id") Pageable pageable,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		Page<Staff> staffs = staffService.getStaffs(loginUser.getStaff(), form.toStaffSearchRequest(), pageable);
		return new PageWrapper<>(staffs);
	}
}
