package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.model.Role;
import jp.co.waja.core.model.staff.*;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.service.team.TeamService;
import jp.co.waja.core.service.worktime.WorkTimeService;
import jp.co.waja.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private TeamService teamService;

	@Autowired
	private WorkTimeService workTimeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Staff> enableStaffs() {
		return staffRepository.findAllByDisabled(false);
	}

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
	public Staff edit(StaffEditRequest request, Long id) {
		Staff staff = staffRepository.findOne(id);
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
		staff.setRole(request.getRole());
		return staffRepository.saveAndFlush(staff);
	}

	public Staff editPassword(Long id, PasswordEditRequest request) throws NotFoundException {
		Staff staff = staffRepository.findOne(id);
		staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Staff initPassword(Long id, PasswordInitRequest request) throws NotFoundException {
		Staff staff = staffRepository.findOne(id);
		staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return staffRepository.saveAndFlush(staff);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Optional<String> delete(Long id) throws NotFoundException, WrongDeleteException {
		Staff staff = staffRepository.findOne(id);
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
}
