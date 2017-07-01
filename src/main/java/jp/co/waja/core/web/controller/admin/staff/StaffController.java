package jp.co.waja.core.web.controller.admin.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/staff")
@Controller
public class StaffController {

	@GetMapping("/list")
	public String list() {
		return "admin/staff/list";
	}

	@GetMapping("/create")
	public String create() {
		return "admin/staff/create";
	}

	@GetMapping("/describe/{staffId}")
	public String describe(@PathVariable Long staffId) {
		return "admin/staff/describe";
	}

	@GetMapping("/edit/{staffId}")
	public String edit(@PathVariable Long staffId) {
		return "admin/staff/edit";
	}
}
