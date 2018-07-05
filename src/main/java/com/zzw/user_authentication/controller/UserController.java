package com.zzw.user_authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@GetMapping(value = "/getUser")
	public String getUser() {
		return "二小放牛郎";
	}

	@GetMapping(value = "/uuu")
	public String getuuu() {
		return "sss";
	}
}
