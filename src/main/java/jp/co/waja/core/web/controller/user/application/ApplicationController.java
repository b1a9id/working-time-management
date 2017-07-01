package jp.co.waja.core.web.controller.user.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("/edit")
	public String edit(Model model) {
		return "user/application/edit";
	}

	@PostMapping("/edit")
	public String edit() {
		return "redirect:/application/list";
	}
}
