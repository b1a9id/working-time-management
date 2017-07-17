package jp.co.waja.domain.service.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.TeamCreateRequest;
import jp.co.waja.domain.repository.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	public List<Team> teams() {
		return teamRepository.findAll();
	}

	public Team create(TeamCreateRequest request) {
		Team team = new Team();
		team.setName(request.getName());
		team.setShortName(request.getShortName());
		return teamRepository.saveAndFlush(team);
	}
}
