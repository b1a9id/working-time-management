package jp.co.waja.core.web.controller.user.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@GetMapping("/describe")
	public String describe(@RequestParam(required = false) Long userId) {
		return "user/profile/describe";
	}
}
