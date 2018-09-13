package com.zzw.user_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzw.user_authentication.domain.entity.SysUser;
import com.zzw.user_authentication.domain.repository.UserRepository;
import com.zzw.user_authentication.service.user.AuthService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping(value = "/login")
	public String login(@RequestBody SysUser user) {
		return authService.login(user.getUsername(), user.getPassword());
	}

	@PostMapping(value = "/register")
	public String register(@RequestBody SysUser user) throws Exception {
		return authService.register(user);
	}

	@PreAuthorize("hasAuthority('admin')")
	@GetMapping(value = "/getUser/{userName}")
	public SysUser getUser(@PathVariable String userName) {
		return userRepository.findByUsername(userName);
	}

}
