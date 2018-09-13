package com.zzw.user_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.zzw.user_authentication.domain.entity.SysPermission;
import com.zzw.user_authentication.domain.repository.PermissionRepository;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@PostMapping(value = "/addPermission")
	public String addPermission(@RequestBody SysPermission permission){
		permission = permissionRepository.save(permission);
		if(!Strings.isNullOrEmpty(permission.getId())){
			return "success";
		}
		return "fail";
	}
}
