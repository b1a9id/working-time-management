package jp.co.waja.core.service.worktime;

import jp.co.waja.app.util.WorkTimeUtils;
import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.worktime.*;
import jp.co.waja.core.repository.worktime.WorkTimeYearMonthRepository;
import jp.co.waja.core.support.WorkTimeUtil;
import jp.co.waja.exception.CannotApproveException;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

import static java.util.Objects.*;
import static jp.co.waja.core.entity.WorkTime.WorkType.*;

@Service
@Transactional
public class WorkTimeService {

	@Autowired
	private WorkTimeYearMonthRepository workTimeYearMonthRepository;

	@Autowired
	private PublicHolidayService publicHolidayService;

	public WorkTimeYearMonth getWorkTimeYearMonth(Long id) {
		return workTimeYearMonthRepository.findOne(id);
	}

	public WorkTimeYearMonth getWorkTimeYearMonth(Staff staff, Long id) {
		return workTimeYearMonthRepository.findOneByStaffAndId(staff, id);
	}

	public WorkTimeYearMonth getWorkTimeYearMonth(Staff staff, int yearMonth) {
		return workTimeYearMonthRepository.findOneByStaffAndWorkYearMonth(staff, yearMonth);
	}

	public List<WorkTimeYearMonth> getNonApprovedWorkTimeYearMonth(List<Staff> staffs) {
		return workTimeYearMonthRepository.findAllByStaffInAndCompletedAtIsNotNullAndApproved1AtIsNull(staffs);
	}

	public WorkTimeYearMonth createWorkTimeYearMonth(Staff staff, int yearMonth) {
		WorkTimeYearMonth workTimeYearMonth = new WorkTimeYearMonth(staff, yearMonth);
		List<WorkTime> workTimes = createWorkTimes(workTimeYearMonth);
		workTimeYearMonth.setWorkTimes(workTimes);
		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	private List<WorkTime> createWorkTimes(WorkTimeYearMonth workTimeYearMonth) {
		Integer yearMonthInt = workTimeYearMonth.getWorkYearMonth();
		List<LocalDate> monthDates = WorkTimeUtils.getMonthDate(WorkTimeUtil.intToYearMonth(yearMonthInt));

		return monthDates.stream()
				.map(monthDate -> new WorkTime(monthDate, getWorkType(monthDate)))
				.collect(Collectors.toList());
	}

	public WorkTimeYearMonth bulkEdit(Staff staff, String displayYearMonth, WorkTimeBulkEditRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth parsedDisplayYearMonth = YearMonth.parse(displayYearMonth, formatter);
		WorkTimeYearMonth workTimeYearMonth = getWorkTimeYearMonth(staff, WorkTimeUtil.yearMonthToInt(parsedDisplayYearMonth));
		workTimeYearMonth.getWorkTimes().forEach(
				workTime -> {
					if (workTime.getWorkType() == WorkTime.WorkType.NORMAL) {
						workTime.setStartAt(request.getStartAt());
						workTime.setEndAt(request.getEndAt());
						workTime.setRestTime(60);
					}
				});
		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	public WorkTimeYearMonth edit(Staff staff, WorkTimeYearMonthEditRequest request) {
		WorkTimeYearMonth workTimeYearMonth = workTimeYearMonthRepository.findOneByStaffAndId(staff, request.getId());
		List<WorkTime> workTimes = request.getWorkTimes().stream()
				.map(editRequest -> {
					WorkTime workTime = new WorkTime(editRequest.getDate(), editRequest.getWorkType());
					workTime.setTrainDelay(editRequest.getTrainDelay());
					workTime.setStartAt(editRequest.getStartAt());
					workTime.setEndAt(editRequest.getEndAt());
					workTime.setRestTime(editRequest.getRestTime());
					workTime.setRemarks(editRequest.getRemarks());
					return workTime;
				}).collect(Collectors.toList());
		workTimeYearMonth.setWorkTimes(workTimes);
		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	public WorkTimeYearMonth complete(Staff completedStaff, Long id, boolean complete) throws NotFoundException {
		WorkTimeYearMonth workTimeYearMonth = getWorkTimeYearMonth(completedStaff, id);
		if (isNull(workTimeYearMonth)) {
			throw new NotFoundException();
		}
		workTimeYearMonth.setCompletedBy(complete ? completedStaff.getName() : null);
		workTimeYearMonth.setCompletedAt(complete ? LocalDateTime.now() : null);

		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	@PreAuthorize("@permissionEvaluatorCustom.hasPermission(authentication, #approve1Staff.role, 'APPROVE_TEAM_WORK_TIME')")
	public WorkTimeYearMonth approve1(Staff approve1Staff, Long id, boolean approve1) throws NotFoundException {
		WorkTimeYearMonth workTimeYearMonth = getWorkTimeYearMonth(id);
		if (isNull(workTimeYearMonth)) {
			throw new NotFoundException();
		}

		String approvedByName = approve1 ? approve1Staff.getName() : null;
		workTimeYearMonth.setApproved1By(approvedByName);
		LocalDateTime now = approve1 ? LocalDateTime.now() : null;
		workTimeYearMonth.setApproved1At(now);
		if (!approve1) {
			workTimeYearMonth.setCompletedBy(null);
			workTimeYearMonth.setCompletedAt(null);
		}

		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	@PreAuthorize("@permissionEvaluatorCustom.hasPermission(authentication, #approve2Staff.role, 'APPROVE_TEAM_WORK_TIME')")
	public WorkTimeYearMonth approve2(Staff approve2Staff, Long id, boolean approve2) throws NotFoundException {
		WorkTimeYearMonth workTimeYearMonth = getWorkTimeYearMonth(id);
		if (isNull(workTimeYearMonth)) {
			throw new NotFoundException();
		}
		if (isNull(workTimeYearMonth.getCompletedAt())) {
			throw new CannotApproveException();
		}

		String approvedByName = approve2 ? approve2Staff.getName() : null;
		workTimeYearMonth.setApproved2By(approvedByName);
		LocalDateTime now = approve2 ? LocalDateTime.now() : null;
		workTimeYearMonth.setApproved2At(now);
		if (!approve2) {
			workTimeYearMonth.setCompletedBy(null);
			workTimeYearMonth.setCompletedAt(null);
		} else if (workTimeYearMonth.getStaff().getTeam().equals(approve2Staff.getTeam())) {
			workTimeYearMonth.setApproved1By(approvedByName);
			workTimeYearMonth.setApproved1At(now);
		}

		return workTimeYearMonthRepository.saveAndFlush(workTimeYearMonth);
	}

	public long countByStaff(Staff staff) {
		return workTimeYearMonthRepository.countByStaff(staff);
	}

	public long countByWorkType(Staff staff, WorkTime.WorkType workType) {
		List<WorkTimeYearMonth> workTimeYearMonths = workTimeYearMonthRepository.findAllByStaff(staff);
		long total = 0;
		for (WorkTimeYearMonth workTimeYearMonth : workTimeYearMonths) {
			long amount = workTimeYearMonth.countByWorkType(workType);
			total += amount;
		}
		return total;
	}

	public boolean isBusinessDay(LocalDate date) {
		if (publicHolidayService.isPublicHolidays(date)) {
			return false;
		}
		return !WorkTimeUtil.isWeekDay(date);
	}

	public List<LocalDate> getBusinessDays(YearMonth yearMonth) {
		if (isNull(yearMonth)) {
			return Collections.emptyList();
		}

		List<LocalDate> dayOfMonths = IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
				.mapToObj(dayOfMonth -> LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), dayOfMonth))
				.filter(date -> !WorkTimeUtil.isWeekDay(date))
				.collect(Collectors.toList());

		List<LocalDate> publicHolidays = publicHolidayService.getPublicHolidays(yearMonth);
		return dayOfMonths.stream()
				.filter(date -> !publicHolidays.contains(date))
				.collect(Collectors.toList());
	}

	public WorkTime.WorkType getWorkType(LocalDate date) {
		if (date == null) {
			return null;
		}
		if (!isBusinessDay(date)) {
			return LEGAL_VACATION;
		}
		return NORMAL;
	}
}
