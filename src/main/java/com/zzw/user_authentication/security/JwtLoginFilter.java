package com.zzw.user_authentication.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzw.user_authentication.domain.entity.SysUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	@Autowired
	public JwtLoginFilter(AuthenticationManager authenticationManager) {

		this.authenticationManager = authenticationManager;
	}

	// 接收并解析用户登陆信息
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			SysUser user = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// 生成token
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = Jwts.builder()
				.setSubject(
						((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
				.signWith(SignatureAlgorithm.HS512, "MyJwtSecret").compact();
		response.addHeader("Authorization", "Bearer " + token);
	}
}
