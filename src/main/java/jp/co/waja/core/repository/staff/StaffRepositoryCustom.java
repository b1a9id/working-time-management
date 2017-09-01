package jp.co.waja.core.repository.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.model.staff.StaffSearchRequest;

import java.util.List;

public interface StaffRepositoryCustom {
	List<Staff> search(StaffSearchRequest request);
}
