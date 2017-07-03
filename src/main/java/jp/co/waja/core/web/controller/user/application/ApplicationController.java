package jp.co.waja.core.web.controller.user.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/application")
public class ApplicationController {

	@GetMapping("/list")
	public String list() {
		return "user/application/list";
	}

	@GetMapping("/create")
	public String create(Model model) {
		return "user/application/create";
	}

	@PostMapping("/create")
	public String create() {
		return "redirect:/application/list";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		return "user/application/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id) {
		return "redirect:/application/list";
	}
}
