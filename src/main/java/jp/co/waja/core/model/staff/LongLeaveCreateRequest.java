package jp.co.waja.core.model.staff;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LongLeaveCreateRequest implements Serializable {

	private List<LongLeaveRequest> longLeaveRequests;
}
