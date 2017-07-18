package jp.co.waja.domain.service.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.entity.Team;
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
}
