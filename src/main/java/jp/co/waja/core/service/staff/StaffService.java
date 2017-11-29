package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.*;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.model.staff.*;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.core.support.ModifiedChecker;
import jp.co.waja.exception.*;
import org.slf4j.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

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

	@PreAuthorize("hasRole('ADMIN')")
	public List<Staff> getStaffsByEmploymentType(Staff.EmploymentType employmentType) {
		return staffRepository.findAllByEmploymentType(employmentType);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public Page<Staff> getStaffs(Staff loginUser, StaffSearchRequest request, Pageable pageable) {
		if (loginUser.getRole() == Role.MANAGER) {
			request.setTeamId(loginUser.getTeam().getId());
		}

		return staffRepository.search(request, pageable);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public List<Staff> getAllByTeam(Staff loginStaff) {
		return staffRepository.findAllByTeam(loginStaff.getTeam());
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Staff> getAllByAdminAndManager() {
		return staffRepository.findAllByRoleIn(Arrays.asList(Role.ADMIN, Role.MANAGER));
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
		Staff staff = staffRepository.findOneById(id);
		Staff unModifiedStaff = new Staff();
		BeanUtils.copyProperties(staff, unModifiedStaff);

		staff.setTeam(request.getTeam());
		staff.setNameLast(request.getNameLast());
		staff.setNameFirst(request.getNameFirst());
		staff.setNameLastKana(request.getNameLastKana());
		staff.setNameFirstKana(request.getNameFirstKana());
		staff.setEmail(request.getEmail());
		staff.setGender(request.getGender());
		staff.setEmploymentType(request.getEmploymentType());
		staff.setEnteredDate(request.getEnteredDate());
		staff.setFlextime(request.isFlextime());
		staff.setTelework(request.isTelework());
		staff.setDisabled(request.isDisabled());
		staff.setRole(request.getRole());
		ModifiedChecker modifiedChecker = new ModifiedChecker();
		List<History> histories = modifiedChecker.check(unModifiedStaff, staff, loginStaff.getStaff().getName());

		if (!CollectionUtils.isEmpty(histories)) {
			staff.getHistories().addAll(histories);
		}

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

	private History addHistory(
			String fieldName,
			String beforeValue,
			String afterValue,
			String updatedBy,
			LocalDateTime updatedAt) {
		History history = new History();
		history.setFieldName(fieldName);
		history.setBeforeValue(beforeValue);
		history.setAfterValue(afterValue);
		history.setUpdatedBy(updatedBy);
		history.setUpdatedAt(updatedAt);

		return history;
	}
}
