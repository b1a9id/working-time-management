package jp.co.waja.app.controller;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.WorkTimeYearMonth;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.service.staff.StaffDetails;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/")
@Controller
public class DashBoardController {

	@Autowired
	private StaffService staffService;

	@Autowired
	private WorkTimeService workTimeService;

	@GetMapping
	public String dashBoard(@AuthenticationPrincipal StaffDetails staffDetails, Model model) {
		Staff loginStaff = staffDetails.getStaff();
		if (loginStaff.getRole() != Role.MANAGER) {
			return "dashboard";
		}
		List<Staff> staffs = staffService.getAllByTeam(loginStaff);
		List<WorkTimeYearMonth> nonApprovedWorkTimeYearMonths = workTimeService.getNonApprovedWorkTimeYearMonth(staffs);
		model.addAttribute("nonApprovedWorkTimeYearMonths", nonApprovedWorkTimeYearMonths);
		return "dashboard";
	}
}
