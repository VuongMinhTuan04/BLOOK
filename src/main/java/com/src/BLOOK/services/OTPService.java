package com.src.BLOOK.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    public String generateOTP(String email) {
        String otp = String.format("%03d-%03d-%03d", new Random().nextInt(1000), new Random().nextInt(1000), new Random().nextInt(1000));
        otpStorage.put(email, otp);
        return otp;
    }

    public void sendOTP(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("BLOOK - MÃ OTP");
        message.setText("Mã OTP của bạn là: " + otp);
        mailSender.send(message);
    }

    public Boolean validateOTP(String email, String otp) {
        return otpStorage.getOrDefault(email, "").equals(otp);
    }
}