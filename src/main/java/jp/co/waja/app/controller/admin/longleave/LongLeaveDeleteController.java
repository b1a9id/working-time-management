package jp.co.waja.app.controller.admin.longleave;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.longleave.LongLeaveService;
import jp.co.waja.core.service.staff.*;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/staffs/long-leave/delete")
public class LongLeaveDeleteController {

	private static final String TARGET_ENTITY_KEY = "staff";

	private static final String FORM_MODEL_KEY = "form";

	private static final String ERRORS_MODEL_KEYS = BindingResult.MODEL_KEY_PREFIX + FORM_MODEL_KEY;

	@Autowired
	private StaffService staffService;

	@Autowired
	private LongLeaveService longLeaveService;

	@ModelAttribute("staffs")
	public List<Staff> setUpStaffs() {
		return staffService.getStaffsByEmploymentType(Staff.EmploymentType.PERMANENT_STAFF);
	}

	@ModelAttribute("types")
	public List<LongLeave.Type> setUpTypes() {
		return Arrays.asList(LongLeave.Type.values());
	}

	@DeleteMapping("/{id}")
	public String delete(
			@AuthenticationPrincipal StaffDetails loginUser,
			@PathVariable Long id,
			RedirectAttributes redirectAttributes) {

		LongLeave longLeave = longLeaveService.getLongLeave(id);
		if (Objects.isNull(longLeave)) {
			throw new NotFoundException();
		}

		longLeaveService.delete(longLeave);

		redirectAttributes.getFlashAttributes().clear();
		redirectAttributes.addFlashAttribute("deleteType", longLeave.getType());
		redirectAttributes.addAttribute("staffId", longLeave.getStaff().getId());
		return "redirect:/admin/staffs/describe/{staffId}";
	}
}
