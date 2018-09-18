package com.zzw.user_authentication.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zzw.user_authentication.config.AuthConfigPropertis;
import com.zzw.user_authentication.domain.dao.PermissionDao;
import com.zzw.user_authentication.domain.entity.SysPermission;
import com.zzw.user_authentication.util.LoginPasswordUtil;
import com.zzw.user_authentication.util.RsaCypherUtil;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private PermissionDao permissionDao;
	private AuthConfigPropertis authConfigPropertis;

	public JwtAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
			PermissionDao permissionDao, AuthConfigPropertis authConfigPropertis) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.permissionDao = permissionDao;
		this.authConfigPropertis = authConfigPropertis;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// 获取认证的用户名 & 密码
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		// 认证逻辑
		UserDetails userDetails = userDetailsService.loadUserByUsername(name);
		if (userDetails == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		if (authConfigPropertis.getIsEncryptPassword()) {
			try {
				byte[] encrypted = Base64.getDecoder().decode(password);
				KeyPair keyPair = LoginPasswordUtil.getUserKeyPair().getIfPresent(name);
				PrivateKey privateKey = keyPair.getPrivate();
                byte[] secret = RsaCypherUtil.decrypt(privateKey, encrypted);
                password = new String(secret);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String oldPassword = userDetails.getPassword();
		if (!bCryptPasswordEncoder.matches(password, oldPassword)) {
			throw new BadCredentialsException("用户名或密码错误");
		}
		// 这里设置权限和角色
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		List<SysPermission> permissions = permissionDao.findPermissionListByUserName(name);
		if (permissions != null) {
			for (SysPermission sysPermission : permissions) {
				// 这里设置权限和角色
				authorities.add(new SimpleGrantedAuthority(sysPermission.getPermissionName()));
			}
		}
		// 生成令牌 这里令牌里面存入了:name,password,authorities, 当然你也可以放其他内容
		Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
		return auth;

	}

	/**
	 * 是否可以提供输入类型的认证服务
	 * 
	 * @param authentication
	 * @return
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
