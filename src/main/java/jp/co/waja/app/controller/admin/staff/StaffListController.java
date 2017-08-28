package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/admin/staffs")
public class StaffListController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@GetMapping
	public String list(Model model) {
		List<Staff> staffs = staffService.staffs();
		Map<Long, Boolean> existWorkTimeMap = new HashMap<>();
		staffs.forEach(staff -> existWorkTimeMap.put(staff.getId(), workTimeService.countByStaff(staff) > 0));

		model.addAttribute("staffs", staffs);
		model.addAttribute("existWorkTimeMap", existWorkTimeMap);
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
