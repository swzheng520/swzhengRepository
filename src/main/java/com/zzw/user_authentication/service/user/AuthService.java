package com.zzw.user_authentication.service.user;

import com.zzw.user_authentication.domain.entity.SysUser;

public interface AuthService {
	String register(SysUser user)throws Exception;

	String login(String username, String password);

	String refresh(String oldToken);
	
	String publicKey(String userName);
}
