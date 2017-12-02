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

		List<History> staffHistories = staff.getHistories();

		Map<LocalDateTime, String> editors = new LinkedHashMap<>();
		staffHistories
				.forEach(history -> {
					if (!editors.keySet().contains(history.getUpdatedAt())) {
						editors.put(history.getUpdatedAt(), history.getUpdatedBy());
					}
				});

		Map<LocalDateTime, List<History>> histories = new LinkedHashMap<>();
		staffHistories.stream()
				.sorted(Comparator.comparing(History::getUpdatedAt).reversed())
				.forEach(history -> histories.put(history.getUpdatedAt(), new ArrayList<>()));
		staffHistories
				.forEach(history -> {
					List<History> list = histories.get(history.getUpdatedAt());
					list.add(history);
					histories.put(history.getUpdatedAt(), list);
				});

		model.addAttribute("staff", staff);
		model.addAttribute("histories", histories);
		model.addAttribute("editors", editors);
		return "admin/staff/history";
	}
}
