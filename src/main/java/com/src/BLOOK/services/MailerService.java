package com.src.BLOOK.services;

import com.src.BLOOK.models.MailInfo;

import jakarta.mail.MessagingException;

public interface MailerService {
	//@throws MessagingException lỗi gửi email
	public void send(MailInfo mailInfo) throws MessagingException; 
	public void send(String to, String subject, String body) throws MessagingException;
	public void queue(MailInfo mailInfo);
	public void queue(String to, String subject, String body);
}
