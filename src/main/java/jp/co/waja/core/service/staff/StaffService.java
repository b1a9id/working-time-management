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
import org.springframework.context.MessageSource;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Boolean.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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

	@Autowired
	private MessageSource messageSource;

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
		if (staffRepository.findOneByCode(request.getCode()).isPresent()) {
			throw new DuplicatedException("code");
		}
		if (nonNull(staffRepository.findOneByEmail(request.getEmail()))) {
			throw new DuplicatedException("email");
		}
		Staff staff = new Staff();
		staff.setCode(request.getCode());
		staff.setTeam(request.getTeam());
		staff.setNameLast(request.getNameLast());
		staff.setNameFirst(request.getNameFirst());
		staff.setNameLastKana(request.getNameLastKana());
		staff.setNameFirstKana(request.getNameFirstKana());
		staff.setEmail(request.getEmail());
		staff.setGender(request.getGender());
		staff.setEmploymentType(request.getEmploymentType());
		staff.setEnteredDate(request.getEnteredDate());
		staff.setWorkTime(request.getWorkTime());
		staff.setFlextime(request.getFlextime());
		staff.setTelework(request.getTelework());
		staff.setDisabled(request.getDisabled());
		staff.setPassword(passwordEncoder.encode(request.getPassword()));
		staff.setRole(request.getRole());
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff edit(StaffDetails loginStaff, StaffEditRequest request, Long id) {
		Optional<Staff> savedStaff = staffRepository.findOneByCode(request.getCode());
		if (savedStaff.isPresent()) {
			savedStaff.
			throw new DuplicatedException("code");
		}
		if (nonNull(staffRepository.findOneByEmail(request.getEmail()))) {
			throw new DuplicatedException("email");
		}

		Staff staff = staffRepository.findOneById(id);
		Staff unModifiedStaff = new Staff();
		BeanUtils.copyProperties(staff, unModifiedStaff);

		staff.setCode(request.getCode());
		staff.setTeam(request.getTeam());
		staff.setNameLast(request.getNameLast());
		staff.setNameFirst(request.getNameFirst());
		staff.setNameLastKana(request.getNameLastKana());
		staff.setNameFirstKana(request.getNameFirstKana());
		staff.setEmail(request.getEmail());
		staff.setGender(request.getGender());
		staff.setEmploymentType(request.getEmploymentType());
		staff.setEnteredDate(request.getEnteredDate());
		staff.setWorkTime(request.getWorkTime());
		staff.setFlextime(request.isFlextime());
		staff.setTelework(request.isTelework());
		staff.setDisabled(request.isDisabled());
		staff.setRole(request.getRole());
		ModifiedChecker modifiedChecker = new ModifiedChecker();
		List<History> histories = modifiedChecker.check(unModifiedStaff, staff, loginStaff.getStaff().getName());

		if (!CollectionUtils.isEmpty(histories)) {
			parseValues(histories);
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
		if (isNull(staff)) {
			throw new NotFoundException("Staff");
		}

		long workTimeCount = workTimeService.countByStaff(staff);
		if (workTimeCount > 0) {
			throw new WrongDeleteException("WorkTimeExist");
		}

		staffRepository.delete(id);
		return Optional.ofNullable(staff.getName());
	}

	private void parseValues(List<History> histories) {
		histories.forEach(history -> {
			String beforeValue;
			String afterValue;
			switch (history.getFieldName()) {
				case "gender":
					beforeValue = getMessage("gender." + history.getBeforeValue(), null);
					afterValue = getMessage("gender." + history.getAfterValue(), null);
					break;
				case "employmentType":
					beforeValue = getMessage("staff.employmenttype." + history.getBeforeValue(), null);
					afterValue = getMessage("staff.employmenttype." + history.getAfterValue(), null);
					break;
				case "enteredDate":
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					beforeValue = formatter.format(LocalDate.parse(history.getBeforeValue()));
					afterValue = formatter.format(LocalDate.parse(history.getAfterValue()));
					break;
				case "flextime":
				case "telework":
					beforeValue = valueOf(history.getBeforeValue()) ? "可" : "不可";
					afterValue = valueOf(history.getAfterValue()) ? "可" : "不可";
					break;
				case "disabled":
					beforeValue = valueOf(history.getBeforeValue()) ? "無効" : "有効";
					afterValue = valueOf(history.getAfterValue()) ? "無効" : "有効";
					break;
				case "role":
					beforeValue = getMessage("role." + history.getBeforeValue(), null);
					afterValue = getMessage("role." + history.getAfterValue(), null);
					break;
				default:
					beforeValue = history.getBeforeValue();
					afterValue = history.getAfterValue();
					break;
			}
			history.setBeforeValue(beforeValue);
			history.setAfterValue(afterValue);
		});
	}
	
	private String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}
}
