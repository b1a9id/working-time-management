package jp.co.waja.core.support;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Component
@ControllerAdvice({"jp.co.waja.app.controller.user.worktime","jp.co.waja.app.controller.admin.worktime"})
public class WorkTimeControllerAdvice {

	@ModelAttribute("workTimeHour")
	public List<Integer> workTimeHour() {
		return WorkTimeUtil.workTimeHour();
	}

	@ModelAttribute("workTimeMinute")
	public List<Integer> workTimeMinute() {
		return WorkTimeUtil.workTimeMinute();
	}
}
