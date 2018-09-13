package com.zzw.user_authentication.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.zzw.user_authentication.domain.entity.SysRolePermission;
import com.zzw.user_authentication.domain.entity.SysUser;
import com.zzw.user_authentication.domain.entity.SysUserRole;
import com.zzw.user_authentication.domain.repository.RolePermissionRepository;
import com.zzw.user_authentication.domain.repository.UserRepository;
import com.zzw.user_authentication.domain.repository.UserRoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public String roleBindToUser(String userId, List<String> roleIds) {
		if (Strings.isNullOrEmpty(userId) || roleIds == null) {
			return "fail";
		}
		SysUser user = userRepository.findById(userId).get();
		if (user == null) {
			return "fail";
		}
		List<SysUserRole> oldList = userRoleRepository.findBySysUserId(userId);
		if (oldList == null) {
			return "fail";
		}
		for (SysUserRole sysUserRole : oldList) {
			userRoleRepository.delete(sysUserRole);
		}
		for (String roleId : roleIds) {
			SysUserRole ur = new SysUserRole();
			ur.setSysRoleId(roleId);
			ur.setSysUserId(userId);
			userRoleRepository.save(ur);
		}
		return "success";
	}

	@Override
	public String permissionBindToRole(String roleId, List<String> permissions) {
		if (Strings.isNullOrEmpty(roleId) || permissions == null) {
			return "fail";
		}
		List<SysRolePermission> oldRoles = rolePermissionRepository.findByRoleId(roleId);
		if (oldRoles == null) {
			return "fail";
		}
		for (SysRolePermission sysRolePermission : oldRoles) {
			rolePermissionRepository.delete(sysRolePermission);
		}
		for (String permissionId : permissions) {
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setPermissionId(permissionId);
			sysRolePermission.setRoleId(roleId);
			rolePermissionRepository.save(sysRolePermission);
		}
		return "success";
	}
}
