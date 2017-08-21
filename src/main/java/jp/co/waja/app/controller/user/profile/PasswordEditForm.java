package jp.co.waja.app.controller.user.profile;

import jp.co.waja.core.model.staff.PasswordEditRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class PasswordEditForm implements Serializable {

	@NotNull
	@Size(min = 8)
	private String oldPassword;

	@NotNull
	@Size(min = 8)
	private String newPassword;

	@NotNull
	@Size(min = 8)
	private String retypePassword;

	public PasswordEditRequest toPasswordEditRequest() {
		return new PasswordEditRequest(getOldPassword(), getNewPassword(), getRetypePassword());
	}
}
