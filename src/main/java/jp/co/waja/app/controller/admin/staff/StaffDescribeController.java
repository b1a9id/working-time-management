package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.domain.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/staffs/describe")
public class StaffDescribeController {

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String describe(
			@RequestParam Long id,
			Model model) {
		Staff staff = staffService.findOneById(id);
		model.addAttribute("staff", staff);
		return "admin/staff/describe";
	}
}
