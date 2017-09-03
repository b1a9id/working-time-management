package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/admin/staffs")
public class StaffListController {

	private static final String FORM_MODEL_KEY = "form";

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@ModelAttribute(FORM_MODEL_KEY)
	public StaffSearchForm setUpStaffSearchForm() {
		return new StaffSearchForm();
	}

	@ModelAttribute("employmentTypes")
	public List<Staff.EmploymentType> setUpWorkTypes() {
		return Arrays.asList(Staff.EmploymentType.values());
	}

	@GetMapping
	public String list(Model model) {
		StaffSearchForm form = (StaffSearchForm) model.asMap().get(FORM_MODEL_KEY);
		form = Optional.ofNullable(form).orElse(new StaffSearchForm());
		List<Staff> staffs = staffService.getStaffs(form.toStaffSearchRequest());
		Map<Long, Boolean> existWorkTimeMap = new HashMap<>();
		staffs.forEach(staff -> existWorkTimeMap.put(staff.getId(), workTimeService.countByStaff(staff) > 0));

		model.addAttribute("staffs", staffs);
		model.addAttribute("existWorkTimeMap", existWorkTimeMap);
		return "admin/staff/list";
	}

	@PostMapping
	public String list(
			@ModelAttribute(FORM_MODEL_KEY) StaffSearchForm form,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute(FORM_MODEL_KEY, form);
		return "redirect:/admin/staffs";
	}
}
