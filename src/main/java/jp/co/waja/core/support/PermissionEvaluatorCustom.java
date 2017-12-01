package jp.co.waja.core.support;

import jp.co.waja.core.model.*;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static java.util.Objects.*;

@Component
public class PermissionEvaluatorCustom implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object targetPermission) {
		if (isNull(authentication) || isNull(targetDomainObject) || isNull(targetPermission)) {
			return false;
		}

		String roleName = targetDomainObject.toString();
		Role role;
		try {
			role = Role.valueOf(roleName);
		} catch (IllegalArgumentException e) {
			return false;
		}

		String permissionName = targetPermission.toString();
		Permission permission;
		try {
			permission = Permission.valueOf(permissionName);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return Permission.getPermissions(role).contains(permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return false;
	}
}
