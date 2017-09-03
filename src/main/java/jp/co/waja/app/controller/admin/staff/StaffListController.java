package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/staffs")
public class StaffListController {

	private static final String FORM_MODEL_KEY = "form";

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute("form")


	@GetMapping
	public String list(Model model) {
		List<Staff> staffs = staffService.enableStaffs();
		Map<Long, Boolean> existWorkTimeMap = new HashMap<>();
		staffs.forEach(staff -> existWorkTimeMap.put(staff.getId(), workTimeService.countByStaff(staff) > 0));

		model.addAttribute("staffs", staffs);
		model.addAttribute("existWorkTimeMap", existWorkTimeMap);
		return "admin/staff/list";
	}

	@PostMapping
	public String list(
			@ModelAttribute StaffSearchForm form,
			Model model) {
		List<Staff> staffs = staffService.getStaffs(form.toStaffSearchRequest());
		return "admin/staff/list";
	}
}
