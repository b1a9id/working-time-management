package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.service.staff.StaffService;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/admin/staffs/edit-history/{id}")
public class StaffEditHistoryController {

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String show(
			@PathVariable Long id,
			Model model) {
		Staff staff = staffService.getStaff(id);
		if (Objects.isNull(staff)) {
			throw new NotFoundException();
		}

		Map<LocalDateTime, List<StaffHistory>> histories = new LinkedHashMap<>();
		staff.getHistories().stream()
				.sorted(Comparator.comparing(StaffHistory::getUpdatedAt).reversed())
				.forEach(staffHistory -> histories.put(staffHistory.getUpdatedAt(), new ArrayList<>()));
		staff.getHistories()
				.forEach(staffHistory -> {
					List<StaffHistory> list = histories.get(staffHistory.getUpdatedAt());
					list.add(staffHistory);
					histories.put(staffHistory.getUpdatedAt(), list);
				});

		model.addAttribute("staff", staff);
		model.addAttribute("histories", histories);
		return "admin/staff/history";
	}
}
