package jp.co.waja.core.model;

import java.util.*;
import java.util.stream.*;

public enum Permission {
	SHOW_MY_PROFILE(PermissionGroup.DEFAULT),
	EDIT_MY_PASSWORD(PermissionGroup.DEFAULT),

	LIST_MY_WORK_TIME(PermissionGroup.DEFAULT),
	COMPLETE_MY_WORK_TIME(PermissionGroup.DEFAULT),
	EDIT_MY_WORK_TIME(PermissionGroup.DEFAULT),
	BULK_EDIT_MY_WORK_TIME(PermissionGroup.DEFAULT),

	LIST_TEAM_STAFF(PermissionGroup.MANAGER),
	DESCRIBE_TEAM_STAFF(PermissionGroup.MANAGER),
	LIST_TEAM_WORK_TIME(PermissionGroup.MANAGER),
	APPROVE_TEAM_WORK_TIME(PermissionGroup.MANAGER),

	LIST_ALL_STAFF(PermissionGroup.ADMIN),
	CREATE_ALL_STAFF(PermissionGroup.ADMIN),
	DESCRIBE_ALL_STAFF(PermissionGroup.ADMIN),
	EDIT_ALL_STAFF(PermissionGroup.ADMIN),
	DELETE_ALL_STAFF(PermissionGroup.ADMIN),
	LIST_ALL_WORK_TIME(PermissionGroup.ADMIN),
	APPROVE_ALL_WORK_TIME(PermissionGroup.ADMIN),
	EDIT_ALL_WORK_TIME(PermissionGroup.ADMIN),

	CREATE_ALL_LONG_VACATION(PermissionGroup.ADMIN),
	DELETE_ALL_LONG_VACATION(PermissionGroup.ADMIN),

	LIST_ALL_TEAM(PermissionGroup.ADMIN),
	CREATE_ALL_TEAM(PermissionGroup.ADMIN),
	EDIT_ALL_TEAM(PermissionGroup.ADMIN),
	DELETE_ALL_TEAM(PermissionGroup.ADMIN);

	private PermissionGroup permissionGroup;

	Permission(PermissionGroup permissionGroup) {
		this.permissionGroup = permissionGroup;
	}

	public PermissionGroup getPermissionGroup() {
		return this.permissionGroup;
	}

	public enum PermissionGroup {
		DEFAULT,
		MANAGER,
		ADMIN
	}

	public static List<Permission> getPermissions(Role role) {
		PermissionGroup group = role.getGroup();
		Stream <Permission> permissions = Arrays.stream(Permission.values());
		if (group == PermissionGroup.DEFAULT) {
			return permissions
					.filter(permission -> permission.getPermissionGroup() == PermissionGroup.DEFAULT)
					.collect(Collectors.toList());
		}
		if (group == PermissionGroup.MANAGER) {
			return permissions
					.filter(permission ->
							permission.getPermissionGroup() == PermissionGroup.DEFAULT || permission.getPermissionGroup() == PermissionGroup.MANAGER)
					.collect(Collectors.toList());
		}
		return permissions.collect(Collectors.toList());
	}
}
