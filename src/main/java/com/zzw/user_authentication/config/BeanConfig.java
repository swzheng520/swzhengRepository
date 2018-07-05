package com.zzw.user_authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class BeanConfig {
	
	@Bean
	public JavaMailSender createJavaMailSenderBean(){
		
		return new JavaMailSenderImpl();
	}
}
