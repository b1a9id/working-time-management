package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.model.team.TeamEditRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class TeamEditForm implements Serializable {

	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String shortName;

	public TeamEditRequest toTeamEditRequest() {
		return new TeamEditRequest(getId(), getName(), getShortName());
	}
}
