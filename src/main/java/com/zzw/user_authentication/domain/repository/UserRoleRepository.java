package com.zzw.user_authentication.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysUserRole;

public interface UserRoleRepository extends JpaRepository<SysUserRole, String> {
	
	List<SysUserRole> findBySysUserId(String userId);

}
