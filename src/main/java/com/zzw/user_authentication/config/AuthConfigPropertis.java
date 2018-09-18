package com.zzw.user_authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthConfigPropertis {
	private boolean isEncryptPassword;

	public boolean getIsEncryptPassword() {
		return isEncryptPassword;
	}

	public void setIsEncryptPassword(boolean isEncryptPassword) {
		this.isEncryptPassword = isEncryptPassword;
	}

}
