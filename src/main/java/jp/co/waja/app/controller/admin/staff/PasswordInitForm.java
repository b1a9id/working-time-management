package jp.co.waja.app.controller.admin.staff;

import jp.co.waja.core.model.staff.*;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
public class PasswordInitForm implements Serializable {

	@NotNull
	@Size(min = 8)
	private String newPassword;

	public PasswordInitRequest toPasswordEditRequest() {
		return new PasswordInitRequest(getNewPassword());
	}
}
