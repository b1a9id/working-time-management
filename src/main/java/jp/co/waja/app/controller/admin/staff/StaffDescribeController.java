package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/admin/staffs/describe/{id}")
public class StaffDescribeController {

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String describe(
			@PathVariable Long id,
			Model model) {
		Staff staff = staffService.findOneById(id);
		if (Objects.isNull(staff)) {
			throw new NotFoundException();
		}
		model.addAttribute("staff", staff);
		return "admin/staff/describe";
	}
}
