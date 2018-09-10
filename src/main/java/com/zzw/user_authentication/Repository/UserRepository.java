package com.zzw.user_authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysUser;

public interface UserRepository extends JpaRepository<SysUser, Long>{
	
	SysUser findByUsername(String userName);
}
