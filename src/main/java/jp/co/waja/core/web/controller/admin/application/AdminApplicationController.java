package jp.co.waja.core.web.controller.admin.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/application")
public class AdminApplicationController {

	@GetMapping("/list")
	public String list() {
		return "admin/application/list";
	}

	@GetMapping("/create")
	public String create(Model model) {
		return "admin/application/create";
	}

	@PostMapping("/create")
	public String create() {
		return "redirect:/admin/application/list";
	}

	@GetMapping("/edit/{applicationId}")
	public String edit(Model model, @PathVariable Long applicationId) {
		return "admin/application/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id) {
		return "redirect:/admin/application/list";
	}
}
