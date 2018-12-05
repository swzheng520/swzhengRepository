package com.zzw.user_authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zzw.user_authentication.domain.dao.PermissionDao;
import com.zzw.user_authentication.security.JwtAuthTokenFilter;
import com.zzw.user_authentication.security.JwtAuthenticationProvider;
import com.zzw.user_authentication.security.JwtLoginFilter;
import com.zzw.user_authentication.service.jwt.JwtTokenService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private JwtConfigPropertis jwtConfigPropertis;
	private JwtTokenService jwtTokenService;
	private PermissionDao permissionDao;
	private AuthConfigPropertis authConfigPropertis;
	
	public WebSecurityConfig(UserDetailsService userDetailsService, JwtConfigPropertis jwtConfigPropertis,
			JwtTokenService jwtTokenService,PermissionDao permissionDao,AuthConfigPropertis authConfigPropertis) {
		this.userDetailsService = userDetailsService;
		this.jwtConfigPropertis = jwtConfigPropertis;
		this.jwtTokenService = jwtTokenService;
		this.permissionDao = permissionDao;
		this.authConfigPropertis = authConfigPropertis;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService, new BCryptPasswordEncoder() ,
				permissionDao,authConfigPropertis));
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user/login").permitAll()
		.antMatchers(HttpMethod.POST, "/user/register").permitAll()
		.antMatchers(HttpMethod.GET, "/user/publicKey/**").permitAll()
		.antMatchers(HttpMethod.GET, "/user/demo").permitAll()
		.anyRequest().authenticated().and()
		.addFilter(new JwtLoginFilter(authenticationManager()))
		.addFilter(new JwtAuthTokenFilter(authenticationManager(), jwtConfigPropertis, jwtTokenService, userDetailsService));
		// 禁用缓存
		httpSecurity.headers().cacheControl();
	}

}