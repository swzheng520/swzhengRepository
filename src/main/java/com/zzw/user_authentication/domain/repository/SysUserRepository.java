package com.zzw.user_authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzw.user_authentication.domain.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}