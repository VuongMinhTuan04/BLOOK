package com.src.BLOOK.implement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.src.BLOOK.models.MailInfo;
import com.src.BLOOK.services.MailerService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailerServiceImplements implements MailerService {
    @Autowired
    private JavaMailSender sender;
    private List<MailInfo> list = new ArrayList<>();

    @Override
    public void send(MailInfo mailInfo) throws MessagingException {
        // Tạo message
        MimeMessage message = sender.createMimeMessage();

        // Dùng helper thiết lập thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mailInfo.getFrom());
        helper.setTo(mailInfo.getTo());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getBody(), true);
        helper.setReplyTo(mailInfo.getFrom());

        // CC
        String[] cc = mailInfo.getCc();
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }

        // BCC
        String[] bcc = mailInfo.getBcc();
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }

        // Attachments
        String[] attachments = mailInfo.getAttachmentPaths();
        if (attachments != null && attachments.length > 0) {
            for (String path : attachments) {
                File file = new File(path);
                helper.addAttachment(file.getName(), file);
            }
        }

        // Gửi message đến SMTP server
        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(new MailInfo(to, subject, body));
    }

    @Override
    public void queue(MailInfo mailInfo) {
        synchronized (list) {
            list.add(mailInfo);
        }
    }

    @Override
    public void queue(String to, String subject, String body) {
        this.queue(new MailInfo(to, subject, body));
    }

    // @Scheduled để khai báo cho phương thức chạy nền thực hiện lấy MailInfo từ hàng đợi và gửi đi (5 giây sẽ kiểm tra và gửi một lần)
    @Scheduled(fixedDelay = 5000)
    public void run() {
        MailInfo mail = null;
        synchronized (list) {
            if (!list.isEmpty()) {
                mail = list.remove(0);
            }
        }
        if (mail != null) {
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
