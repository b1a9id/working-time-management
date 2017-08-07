package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.entity.Team;
import jp.co.waja.core.model.team.TeamEditRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TeamEditForm implements Serializable {

	@NotNull
	private String name;

	@NotNull
	private String shortName;

	public TeamEditForm(Team team) {
		this.name = team.getName();
		this.shortName = team.getShortName();
	}

	public TeamEditRequest toTeamEditRequest() {
		return new TeamEditRequest(getName(), getShortName());
	}
}
