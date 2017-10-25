package jp.co.waja.core.model.staff;

import jp.co.waja.core.entity.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LongLeaveRequest implements Serializable {

	private Long staffId;

	private LongLeave.Type type;

	private LocalDate startAt;

	private LocalDate endAt;

	private String remarks;
}
