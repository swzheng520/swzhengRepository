package com.zzw.user_authentication.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Resource
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public boolean sendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sender);
		message.setSubject("问候");
		message.setText("你最近过的怎么样？？");
		message.setTo("2604910803@qq.com","2228770297@qq.com","417273437@qq.com");
		try {
			javaMailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
