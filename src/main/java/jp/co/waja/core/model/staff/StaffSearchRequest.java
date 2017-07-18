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
public class StaffSearchRequest implements Serializable {

	private Long id;

	private Long teamId;
}
