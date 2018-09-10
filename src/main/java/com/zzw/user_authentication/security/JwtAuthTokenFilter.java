package com.zzw.user_authentication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.zzw.user_authentication.config.JwtConfigPropertis;
import com.zzw.user_authentication.service.jwt.JwtTokenService;

public class JwtAuthTokenFilter extends BasicAuthenticationFilter {

	private UserDetailsService userDetailsService;
	private JwtTokenService jwtTokenService;
	private JwtConfigPropertis jwtConfigPropertis;

	public JwtAuthTokenFilter(AuthenticationManager authenticationManager, JwtConfigPropertis jwtConfigPropertis,
			JwtTokenService jwtTokenService, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtConfigPropertis = jwtConfigPropertis;
		this.jwtTokenService = jwtTokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String header = jwtConfigPropertis.getHeader();
		String authHeader = request.getHeader(header);
		if (authHeader != null && authHeader.startsWith(jwtConfigPropertis.getTokenHead())) {
			final String authToken = authHeader.substring(jwtConfigPropertis.getTokenHead().length() + 1);
			String username = jwtTokenService.getUsernameFromToken(authToken);
			logger.info("checking authentication " + username);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (jwtTokenService.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					logger.info("authenticated user " + username + ", setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);
	}
}
