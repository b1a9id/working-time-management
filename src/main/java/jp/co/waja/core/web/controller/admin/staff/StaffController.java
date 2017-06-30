package jp.co.waja.core.web.controller.admin.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@GetMapping("/list")
	public String list() {
		return "admin/staff/list";
	}
}
