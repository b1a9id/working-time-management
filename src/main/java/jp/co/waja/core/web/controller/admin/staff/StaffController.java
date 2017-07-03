package jp.co.waja.core.web.controller.admin.staff;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@GetMapping("/list")
	public String list() {
		return "admin/staff/list";
	}

	@GetMapping("/create")
	public String create(Model model) {
		return "admin/staff/create";
	}

	@PostMapping("/create")
	public String create() {
		return "redirect:/staff/describe/1";
	}

	@GetMapping("/describe/{staffId}")
	public String describe(@PathVariable Long staffId) {
		return "admin/staff/describe";
	}

	@GetMapping("/edit/{staffId}")
	public String edit(Model model, @PathVariable Long staffId) {
		return "admin/staff/edit";
	}

	@PostMapping("/edit/{staffId}")
	public String edit(@PathVariable Long staffId) {
		return "redirect:/staff/list";
	}

	@GetMapping("/histories/{staffId}")
	public String history(@PathVariable Long staffId) {
		return "admin/staff/history";
	}
}
