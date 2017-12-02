package jp.co.waja.core.model;

public enum Role {
	ADMIN(Permission.PermissionGroup.ADMIN),
	MANAGER(Permission.PermissionGroup.MANAGER),
	STAFF(Permission.PermissionGroup.DEFAULT),
	CREW(Permission.PermissionGroup.DEFAULT);

	private final Permission.PermissionGroup group;

	Role (final Permission.PermissionGroup group) {
		this.group = group;
	}

	public Permission.PermissionGroup getGroup() {
		return this.group;
	}
}
