package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.model.staff.PasswordEditRequest;
import lombok.*;

import javax.validation.constraints.*;
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
