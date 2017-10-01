package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.model.staff.StaffSearchRequest;
import org.springframework.data.domain.*;

public interface StaffRepositoryCustom {
	Page<Staff> search(StaffSearchRequest request, Pageable pageable);
}
