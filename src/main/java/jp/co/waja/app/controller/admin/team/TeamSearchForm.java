package jp.co.waja.app.controller.admin.team;

import jp.co.waja.core.model.TeamSearchRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TeamSearchForm implements Serializable {

	private Long id;

	public TeamSearchRequest toTeamSearchRequest() {
		return new TeamSearchRequest(getId());
	}
}
