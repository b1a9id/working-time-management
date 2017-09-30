package jp.co.waja.config;

import jp.co.waja.core.service.staff.StaffDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WtmAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
			return null;
		}
		return ((StaffDetails) authentication.getPrincipal()).getStaff().getName();
	}
}
