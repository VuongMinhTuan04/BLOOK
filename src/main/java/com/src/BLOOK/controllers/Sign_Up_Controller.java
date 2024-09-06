package com.src.BLOOK.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.src.BLOOK.models.Account;
import com.src.BLOOK.models.Role_Account;
import com.src.BLOOK.repository.AccountRepository;
import com.src.BLOOK.services.OTPService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Sign_Up_Controller {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OTPService otpService;

    @GetMapping("/BLOOK/sign-up")
    public String doGet() {
        return "account/sign-up";
    }

    @PostMapping("/BLOOK/sign-in")
    public String signUp(
            Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullname") String fullname,
            @RequestParam("email") String email,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        Optional<Account> optional = accountRepository.findByUsername(username);

        if (optional.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "redirect:/BLOOK/sign-up";
        }

        // Tạo và gửi mã OTP
        String otp = otpService.generateOTP(email);
        otpService.sendOTP(email, otp);

        // Lưu thông tin người dùng vào session
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("fullname", fullname);
        session.setAttribute("email", email);
        session.setAttribute("phone_number", phoneNumber);
        session.setAttribute("file", multipartFile);

        return "redirect:/BLOOK/verify-otp";
    }

    @GetMapping("/BLOOK/verify-otp")
    public String verifyOTP(
            HttpSession session,
            @RequestParam("otp") String otp,
            RedirectAttributes redirectAttributes
    ) {
        String email = (String) session.getAttribute("email");

        if (otpService.validateOTP(email, otp)) {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            String fullname = (String) session.getAttribute("fullname");
            String phoneNumber = (String) session.getAttribute("phone_number");
            MultipartFile multipartFile = (MultipartFile) session.getAttribute("file");

            Account account = new Account();
            account.setUsername(username);
            account.setEmail(email);
            account.setPassword(password);
            account.setFullname(fullname);
            account.setPhone_number(phoneNumber);
            account.setRole(new Role_Account(false));

            if (multipartFile != null && !multipartFile.isEmpty()) {
                try {
                    String fileName = multipartFile.getOriginalFilename();
                    String uploadDir = "src/main/resources/static/img-avatar";
                    Path path = Paths.get(uploadDir + "/" + fileName);
                    Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    account.setAvatar(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "Tải lên tập tin thất bại!");
                    return "redirect:/BLOOK/sign-up";
                }
            }

            accountRepository.save(account);
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công!");
            return "redirect:/BLOOK/sign-up";
        } else {
            redirectAttributes.addFlashAttribute("error", "Mã OTP không hợp lệ!");
            return "redirect:/BLOOK/verify-otp";
        }
    }
}
