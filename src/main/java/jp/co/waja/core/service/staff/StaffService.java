package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.model.staff.*;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.exception.NotFoundException;
import jp.co.waja.exception.WrongDeleteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private WorkTimeService workTimeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Staff getStaff(long id) {
		return staffRepository.findOne(id);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public Page<Staff> getStaffs(Staff loginUser, StaffSearchRequest request, Pageable pageable) {
		if (loginUser.getRole() == Role.MANAGER) {
			request.setTeamId(loginUser.getTeam().getId());
		}

		return staffRepository.search(request, pageable);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff create(StaffCreateRequest request) {
		Staff staff = new Staff();
		staff.setTeam(request.getTeam());
		staff.setNameLast(request.getNameLast());
		staff.setNameFirst(request.getNameFirst());
		staff.setNameLastKana(request.getNameLastKana());
		staff.setNameFirstKana(request.getNameFirstKana());
		staff.setEmail(request.getEmail());
		staff.setGender(request.getGender());
		staff.setEmploymentType(request.getEmploymentType());
		staff.setEnteredDate(request.getEnteredDate());
		staff.setFlextime(request.getFlextime());
		staff.setTelework(request.getTelework());
		staff.setDisabled(request.getDisabled());
		staff.setPassword(passwordEncoder.encode(request.getPassword()));
		staff.setRole(request.getRole());
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff edit(StaffDetails loginStaff, StaffEditRequest request, Long id) {
		List<StaffHistory> histories = new LinkedList<>();
		String updatedBy = loginStaff.getStaff().getName();
		LocalDateTime updatedAt = LocalDateTime.now();

		Staff staff = staffRepository.findOneById(id);
		if (!staff.getTeam().getId().equals(request.getTeam().getId())) {
			StaffHistory history = addHistory("team", staff.getTeam().getName(), request.getTeam().getName(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setTeam(request.getTeam());

		if (!staff.getNameLast().equals(request.getNameLast()) || !staff.getNameFirst().equals(request.getNameFirst())) {
			StaffHistory history = addHistory("name", staff.getName(), request.getNameLast() + request.getNameFirst(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setNameLast(request.getNameLast());
		staff.setNameFirst(request.getNameFirst());

		if (!staff.getNameLastKana().equals(request.getNameLastKana()) || !staff.getNameFirstKana().equals(request.getNameFirstKana())) {
			StaffHistory history = addHistory("nameKana", staff.getNameKana(), request.getNameLastKana() + request.getNameFirstKana(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setNameLastKana(request.getNameLastKana());
		staff.setNameFirstKana(request.getNameFirstKana());

		if (!staff.getEmail().equals(request.getEmail())) {
			StaffHistory history = addHistory("email", staff.getEmail(), request.getEmail(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setEmail(request.getEmail());

		if (staff.getGender() != request.getGender()) {
			StaffHistory history = addHistory("gender", staff.getGender().name(), request.getGender().name(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setGender(request.getGender());

		if (staff.getEmploymentType() != request.getEmploymentType()) {
			StaffHistory history = addHistory("employmentType", staff.getEmploymentType().name(), request.getEmploymentType().name(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setEmploymentType(request.getEmploymentType());

		if (!staff.getEnteredDate().isEqual(request.getEnteredDate())) {
			StaffHistory history = addHistory("enteredDate", staff.getEnteredDate().toString(), request.getEnteredDate().toString(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setEnteredDate(request.getEnteredDate());

		if (staff.isFlextime() != request.isFlextime()) {
			StaffHistory history = addHistory("flextime", String.valueOf(staff.isFlextime()), String.valueOf(request.isFlextime()), updatedBy, updatedAt);
			histories.add(history);
		}

		if (staff.isTelework() != request.isTelework()) {
			StaffHistory history = addHistory("telework", String.valueOf(staff.isTelework()), String.valueOf(request.isTelework()), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setTelework(request.isTelework());

		if (staff.isDisabled() != request.isDisabled()) {
			StaffHistory history = addHistory("disabled", String.valueOf(staff.isDisabled()), String.valueOf(request.isDisabled()), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setDisabled(request.isDisabled());

		if (staff.getRole() != request.getRole()) {
			StaffHistory history = addHistory("role", staff.getRole().name(), request.getRole().name(), updatedBy, updatedAt);
			histories.add(history);
		}
		staff.setRole(request.getRole());

		staff.getHistories().addAll(histories);

		return staffRepository.saveAndFlush(staff);
	}

	public Staff editPassword(Long id, PasswordEditRequest request) throws NotFoundException {
		Staff staff = staffRepository.findOneById(id);
		staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff initPassword(Long id, PasswordInitRequest request) throws NotFoundException {
		Staff staff = staffRepository.findOneById(id);
		staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Optional<String> delete(Long id) throws NotFoundException, WrongDeleteException {
		Staff staff = staffRepository.findOneById(id);
		if (Objects.isNull(staff)) {
			throw new NotFoundException("Staff");
		}

		long workTimeCount = workTimeService.countByStaff(staff);
		if (workTimeCount > 0) {
			throw new WrongDeleteException("WorkTimeExist");
		}

		staffRepository.delete(id);
		return Optional.ofNullable(staff.getName());
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff longLeaveCreate(LongLeaveCreateRequest request, Long id) {
		Staff staff = staffRepository.findOneById(id);
		List<LongLeave> longLeaves = request.getLongLeaveRequests().stream()
				.map(longLeaveRequest -> {
					LongLeave longLeave = new LongLeave();
					longLeave.setType(longLeaveRequest.getType());
					longLeave.setStartAt(longLeaveRequest.getStartAt());
					longLeave.setEndAt(longLeaveRequest.getEndAt());
					longLeave.setRemarks(longLeaveRequest.getRemarks());
					return longLeave;
				})
				.collect(Collectors.toList());
		staff.getLongLeaves().addAll(longLeaves);
		return staffRepository.saveAndFlush(staff);
	}

	private StaffHistory addHistory(
			String fieldName,
			String beforeValue,
			String afterValue,
			String updatedBy,
			LocalDateTime updatedAt) {
		StaffHistory staffHistory = new StaffHistory();
		staffHistory.setFieldName(fieldName);
		staffHistory.setBeforeValue(beforeValue);
		staffHistory.setAfterValue(afterValue);
		staffHistory.setUpdatedBy(updatedBy);
		staffHistory.setUpdatedAt(updatedAt);

		return staffHistory;
	}
}
