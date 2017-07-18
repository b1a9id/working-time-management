package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.model.team.TeamCreateRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class TeamCreateForm implements Serializable {

	@NotNull
	private String name;

	@NotNull
	private String shortName;

	public TeamCreateRequest toTeamCreateRequest() {
		return new TeamCreateRequest(getName(), getShortName());
	}
}
