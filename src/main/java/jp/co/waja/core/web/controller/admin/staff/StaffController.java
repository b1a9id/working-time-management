package jp.co.waja.core.web.controller.admin.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/staff")
@Controller
public class StaffController {

	@GetMapping("/list")
	public String list() {
		return "admin/staff/list";
	}
}
