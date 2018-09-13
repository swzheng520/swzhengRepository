package com.zzw.user_authentication.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null || configAttributes.size() == 0) {
			return;
		}
		Collection<? extends GrantedAuthority> auths = authentication.getAuthorities();
		for (ConfigAttribute configAttribute : configAttributes) {
			String role = configAttribute.getAttribute();
			for (GrantedAuthority auth : auths) {
				if (role.trim().equals(auth.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("Access Denied");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
