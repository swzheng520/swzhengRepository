package com.zzw.user_authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysPermission;

public interface PermissionRepository extends JpaRepository<SysPermission, String>{
	

}
