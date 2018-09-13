package com.zzw.user_authentication.service.user;

import java.util.List;

public interface RoleService {
	
	public String roleBindToUser(String userId,List<String> roleIds);
	
	public String permissionBindToRole(String roleId,List<String> permissions);
}
