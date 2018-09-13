package com.zzw.user_authentication.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.zzw.user_authentication.domain.entity.SysPermission;
import com.zzw.user_authentication.domain.repository.PermissionRepository;

@Component
public class JwtFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		System.out.println(request.getRequestURL());
		List<SysPermission> permissions = permissionRepository.findAll();
		if (permissions == null) {
			return null;
		}
		for (SysPermission sysPermission : permissions) {
			AntPathRequestMatcher matcher = new AntPathRequestMatcher(sysPermission.getUrl());
			if (matcher.matches(request)) {
				Collection<ConfigAttribute> array = new ArrayList<>();
				SecurityConfig securityConfig = new SecurityConfig(sysPermission.getPermissionName());
				array.add(securityConfig);
				return array;
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
