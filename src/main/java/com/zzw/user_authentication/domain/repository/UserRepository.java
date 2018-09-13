package com.zzw.user_authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysUser;

public interface UserRepository extends JpaRepository<SysUser,String> {
    SysUser findByUsername(String username);
}