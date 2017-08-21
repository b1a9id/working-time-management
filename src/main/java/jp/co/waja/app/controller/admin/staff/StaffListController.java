package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/staffs")
public class StaffListController {

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String list(Model model) {
		List<Staff> staffs = staffService.staffs();
		model.addAttribute("staffs", staffs);
		return "admin/staff/list";
	}

	@GetMapping("/{teamId}")
	public String list(
			@PathVariable Long teamId,
			Model model) {
		StaffSearchForm form = new StaffSearchForm();
		form.setTeamId(teamId);
		List<Staff> staffs = staffService.staffsByTeam(form.toStaffSearchRequest());
		model.addAttribute("staffs", staffs);
		return "admin/staff/list";
	}
}
