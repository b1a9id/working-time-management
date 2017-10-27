package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin/staffs/describe/{id}")
public class StaffDescribeController {

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String describe(@PathVariable Long id, Model model) {
		Staff staff = staffService.getStaff(id);
		if (Objects.isNull(staff)) {
			throw new NotFoundException();
		}

		staff.setLongLeaves(staff.sortByEndAt());
		model.addAttribute("staff", staff);
		model.addAttribute("duringLongLeave", duringLongLeave(staff.getLongLeaves()));

		return "admin/staff/describe";
	}

	private LongLeave duringLongLeave(List<LongLeave> longLeaves) {
		LocalDate today = LocalDate.now();
		return longLeaves.stream()
				.filter(longLeave -> isDuringLongLeave(today, longLeave.getStartAt(), longLeave.getEndAt()))
				.findFirst()
				.orElse(null);
	}

	private boolean isDuringLongLeave(LocalDate target, LocalDate startAt, LocalDate endAt) {
		if (target.isEqual(startAt) || target.isEqual(endAt)) {
			return true;
		}
		if (target.isAfter(startAt) && target.isBefore(endAt)) {
			return true;
		}
		return false;
	}
}
