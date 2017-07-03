package jp.co.waja.core.web.controller.user.application;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> Stashed changes

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

	@GetMapping("/edit/{applicationId}")
	public String edit(Model model, @PathVariable Long applicationId) {
		return "user/application/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id) {
		return "redirect:/application/list";
	}
}
