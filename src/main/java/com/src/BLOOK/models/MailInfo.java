package com.src.BLOOK.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MailInfo {
	private String from;
	private String to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private String body;
	private MultipartFile[] attachments;
	private String[] attachmentPaths;
	
	public MailInfo() {
        this.from = "vuongtuan1357@gmail.com";
    }
	
	public MailInfo(String from, String to, String[] cc, String[] bcc, String subject, String body, String[] attachmentPaths) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.attachmentPaths = attachmentPaths;
    }
	
	public MailInfo(String to, String subject, String body) {
		super();
		this.from = "FPT Polytechnic <vuongtuan1357@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
