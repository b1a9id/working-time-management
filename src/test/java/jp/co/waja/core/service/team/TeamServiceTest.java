package jp.co.waja.core.service.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.repository.staff.StaffRepository;
import jp.co.waja.core.repository.team.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class TeamServiceTest {

	@InjectMocks
	private TeamService teamService;

	@Mock
	private TeamRepository teamRepository;

	@Mock
	private StaffRepository staffRepository;

	@Before
	public void setup() {
		initMocks(this);
	}

	private Team generateTeam(String name, String shortName) {
		Team team = new Team();
		team.setName(name);
		team.setShortName(shortName);
		return team;
	}

	@Test
	public void getTeams() {
		Team cr = generateTeam("クリエイティブ", "CR");
		Team jisui = generateTeam("事業推進室", "じすい");
		List<Team> expectedTeams = Arrays.asList(cr, jisui);

		Mockito.when(teamRepository.findAll()).thenReturn(expectedTeams);
		List<Team> teams = teamService.getTeams();
		Assertions.assertThat(teams)
				.containsExactly(cr, jisui);
	}
}
