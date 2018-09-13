package com.zzw.user_authentication.domain.request;

import java.util.List;

public class RolePermissionRequest {
	private String roleId;
	private List<String> permissions;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}


}
