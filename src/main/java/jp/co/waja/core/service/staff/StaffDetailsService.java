package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.Staff;
import jp.co.waja.core.repository.staff.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StaffDetailsService implements UserDetailsService {

	@Autowired
	private StaffRepository staffRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Staff staff = staffRepository.findOneByEmail(username);
		if (Objects.isNull(staff)) {
			throw new UsernameNotFoundException(username + "is not found.");
		}
		return new StaffDetails(staff);
	}
}
