package com.zzw.user_authentication.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysRolePermission;

public interface RolePermissionRepository extends JpaRepository<SysRolePermission, String> {

	List<SysRolePermission> findByRoleId(String roleId);

}
