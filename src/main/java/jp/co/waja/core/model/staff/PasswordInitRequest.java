package jp.co.waja.core.model.staff;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordInitRequest implements Serializable {

	private String newPassword;
}
