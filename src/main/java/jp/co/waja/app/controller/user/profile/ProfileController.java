package jp.co.waja.app.controller.user.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@GetMapping("/describe/{userId}")
	public String describe(@RequestParam(required = false) Long userId) {
		return "user/profile/describe";
	}

	@GetMapping("/password/edit/{userId}")
	public String passwordEdit(@RequestParam(required = false) Long userId) {
		return "user/profile/password/edit";
	}

	@PostMapping("/password/edit")
	public String passwordEdit() {
		return "redirect:/profile/describe";
	}
}