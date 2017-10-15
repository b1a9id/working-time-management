package jp.co.waja.core.service.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.team.*;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.repository.team.TeamRepository;
import jp.co.waja.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private StaffRepository staffRepository;

	public List<Team> getTeams() {
		return teamRepository.findAll();
	}

	public Team getTeam(long teamId) {
		return teamRepository.findOne(teamId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Team create(TeamCreateRequest request) {
		Team team = new Team();
		team.setName(request.getName());
		team.setShortName(request.getShortName());
		return teamRepository.saveAndFlush(team);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Team edit(TeamEditRequest request, Long id) {
		Optional<Team> teamOptional = Optional.ofNullable(teamRepository.findOneById(id));
		if (!teamOptional.isPresent()) {
			throw new NotFoundException();
		}

		Team team = teamOptional.get();
		team.setName(request.getName());
		team.setShortName(request.getShortName());
		return teamRepository.saveAndFlush(team);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Optional<String> delete(Long id) throws NotFoundException, WrongDeleteException {
		Team team = teamRepository.findOneById(id);
		if (Objects.isNull(team)) {
			throw new NotFoundException("team");
		}

		long staffCount = staffRepository.countByTeam(team);
		if (staffCount > 0) {
			throw new WrongDeleteException("StaffExist");
		}

		teamRepository.delete(id);
		return Optional.ofNullable(team.getName());
	}
}
