package jp.co.waja.domain.service.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.staff.StaffCreateRequest;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import jp.co.waja.domain.repository.staff.StaffRepository;
import jp.co.waja.domain.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private TeamRepository teamRepository;

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
		staff.setPassword(request.getPassword());
		return staffRepository.saveAndFlush(staff);
	}

	public void delete(Long id) {
		teamRepository.delete(id);
	}
}
