package com.zzw.user_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.zzw.user_authentication.domain.entity.SysRole;
import com.zzw.user_authentication.domain.repository.RoleRepository;
import com.zzw.user_authentication.domain.request.RolePermissionRequest;
import com.zzw.user_authentication.domain.request.UserRoleRequest;
import com.zzw.user_authentication.service.user.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleService roleService;

	@PostMapping(value = "/addRole")
	private String addRole(@RequestBody SysRole role) {
		role = roleRepository.save(role);
		if (!Strings.isNullOrEmpty(role.getId())) {
			return "success";
		}
		return "fail";
	}

	@PostMapping(value = "/roleBindToUser")
	public String roleBindToUser(@RequestBody UserRoleRequest request) {
		return roleService.roleBindToUser(request.getUserId(), request.getRoles());
	}

	@PostMapping(value = "/permissionBindToRole")
	public String permissionBindToRole(@RequestBody RolePermissionRequest request) {
		return roleService.permissionBindToRole(request.getRoleId(), request.getPermissions());
	}
}
