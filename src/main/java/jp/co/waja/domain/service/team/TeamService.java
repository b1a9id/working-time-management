package jp.co.waja.domain.service.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.team.TeamCreateRequest;
import jp.co.waja.core.model.team.TeamEditRequest;
import jp.co.waja.domain.repository.team.TeamRepository;
import jp.co.waja.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	public List<Team> teams() {
		return teamRepository.findAll();
	}

	public Team findOneById(long teamId) {
		return teamRepository.findOneById(teamId);
	}

	public Team create(TeamCreateRequest request) {
		Team team = new Team();
		team.setName(request.getName());
		team.setShortName(request.getShortName());
		return teamRepository.saveAndFlush(team);
	}

	public Team edit(TeamEditRequest request) {
		Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOneById(request.getId()));
		if (!teamOptional.isPresent()) {
			throw new NotFoundException();
		}

		Team team = teamOptional.get();
		team.setName(request.getName());
		team.setShortName(request.getShortName());
		return teamRepository.saveAndFlush(team);
	}

	public void delete(Long id) {
		teamRepository.delete(id);
	}
}
