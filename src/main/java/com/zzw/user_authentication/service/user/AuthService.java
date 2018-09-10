package com.zzw.user_authentication.service.user;

import com.zzw.user_authentication.domain.entity.SysUser;

public interface AuthService {
	SysUser register(SysUser user);

	String login(String username, String password);

	String refresh(String oldToken);
}
