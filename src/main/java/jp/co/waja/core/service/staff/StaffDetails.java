package jp.co.waja.core.service.staff;

import jp.co.waja.core.entity.Staff;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class StaffDetails implements UserDetails {

	@Getter
	private final Staff staff;

	public StaffDetails(Staff staff) {
		this.staff = staff;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_" + this.staff.getRole().name());
	}

	@Override
	public String getPassword() {
		return this.staff.getPassword();
	}

	@Override
	public String getUsername() {
		return this.staff.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
