package com.zzw.user_authentication.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityEntity implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1167430310847050503L;
	private String url;
	private String method;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public GrantedAuthorityEntity(String url, String method) {
		this.url = url;
		this.method = method;
	}

	@Override
	public String getAuthority() {

		return this.url + ";" + this.method;
	}
}
