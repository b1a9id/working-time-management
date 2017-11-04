package jp.co.waja.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.MapUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("security.roles")
@Component
public class SecurityRolesProperties {

	private Map<String, List<String>> hierarchyMap = new LinkedHashMap<>();

	public Map<String, List<String>> getHierarchyMap() {
		return hierarchyMap;
	}

	public void setHierarchyMap(Map<String, List<String>> hierarchyMap) {
		this.hierarchyMap = hierarchyMap;
	}

	public RoleHierarchy getRoleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = MapUtils.isEmpty(hierarchyMap) ? "" : RoleHierarchyUtils.roleHierarchyFromMap(hierarchyMap);
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}
}
