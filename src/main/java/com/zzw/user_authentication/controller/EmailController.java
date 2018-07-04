package com.zzw.user_authentication.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzw.user_authentication.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Resource
	private EmailService emailService;
	@GetMapping(value ="/sendEmail")
	public String sendEmail(){
		boolean result = emailService.sendEmail();
		if(result){
			return "success";
		}
		return "faild";
	}
	
}
