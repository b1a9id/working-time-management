package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.staff.PasswordEditRequest;
import jp.co.waja.core.model.staff.StaffCreateRequest;
import jp.co.waja.core.model.staff.StaffEditRequest;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.repository.team.TeamRepository;
import jp.co.waja.core.repository.worktime.WorkTimeYearMonthRepository;
import jp.co.waja.exception.NotFoundException;
import jp.co.waja.exception.WrongDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private WorkTimeYearMonthRepository workTimeYearMonthRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Staff> staffs() {
		return staffRepository.findAll();
	}

	public List<Staff> staffsByTeam(StaffSearchRequest request) {
		Team team = teamRepository.findOneById(request.getTeamId());
		return staffRepository.findAllByTeam(team);
	}

	public Staff findOneById(long id) {
		return staffRepository.findOne(id);
	}

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
		staff.setTelework(request.getTelework());
		staff.setPassword(passwordEncoder.encode(request.getPassword()));
		staff.setRole(request.getRole());
		return staffRepository.saveAndFlush(staff);
	}

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
		staff.setTelework(request.getTelework());
		staff.setRole(request.getRole());
		return staffRepository.saveAndFlush(staff);
	}

	public Staff editPassword(Long id, PasswordEditRequest request) throws NotFoundException {
		Staff staff = staffRepository.findOne(id);
		staff.setPassword(passwordEncoder.encode(request.getNewPassword()));
		return staffRepository.saveAndFlush(staff);
	}

	public Optional<String> delete(Long id) throws NotFoundException, WrongDeleteException {
		Staff staff = staffRepository.findOne(id);
		if (Objects.isNull(staff)) {
			throw new NotFoundException("Staff");
		}

		long workTimeCount = workTimeYearMonthRepository.countByStaff(staff);
		if (workTimeCount > 0) {
			throw new WrongDeleteException("WorkTimeExist");
		}

		staffRepository.delete(id);
		return Optional.ofNullable(staff.getName());
	}
}
