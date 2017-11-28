package jp.co.waja.config;

import jp.co.waja.core.service.staff.StaffDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private StaffDetailsService staffDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				.userDetailsService(this.staffDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RoleHierarchy roleHierarchy(SecurityRolesProperties rolesProperties) {
		return rolesProperties.getRoleHierarchy();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/webjars/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
				.antMatchers("/**").authenticated()
				.and()
				.formLogin()
				.loginPage("/login-form")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-form?error=true").permitAll()
				.and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.rememberMe()
				.key("worldrobe")
				.useSecureCookie(true);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(staffDetailsService).passwordEncoder(passwordEncoder());
	}
}
