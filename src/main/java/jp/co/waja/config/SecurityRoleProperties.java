package jp.co.waja.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@ConfigurationProperties("security.roles")
@Component
@Getter
@Setter
public class SecurityRoleProperties {

	private Map<String, List<String>> hierarchyMap = new LinkedHashMap<>();

	public RoleHierarchy getRoleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = isEmpty(hierarchyMap) ? "" : RoleHierarchyUtils.roleHierarchyFromMap(hierarchyMap);
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}
}
