package com.zzw.user_authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysRole;

public interface RoleRepository extends JpaRepository<SysRole, String>{
	
	SysRole findByName(String name);
	
}
