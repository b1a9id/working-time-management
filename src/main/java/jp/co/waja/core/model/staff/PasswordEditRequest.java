package jp.co.waja.core.model.staff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordEditRequest implements Serializable {

	private String oldPassword;

	private String newPassword;

	private String retypePassword;
}
