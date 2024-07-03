package com.nthnetchill.controller;

import com.nthnetchill.request.OTPRequest;
import com.nthnetchill.security.jwt.EmailUtil;
import com.nthnetchill.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/otps/")
public class OTPController {
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestBody String email) {
        try {
            String otp = generateOTP(); // Tạo mã OTP ngẫu nhiên
            String token = jwtUtils.generateOTP(email, otp); // Tạo token từ mã OTP
            EmailUtil.sendOTPEmail(email, otp); // Gửi email chứa mã OTP
            return ResponseEntity.ok(token);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody OTPRequest otpRequest) {
        String token = otpRequest.getToken();
        String otp = otpRequest.getOtp();

        boolean otpVerified = jwtUtils.verifyOTP(token, otp);
        if (otpVerified) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    private String generateOTP() {
        int otp = (int) (Math.random() * 900000 + 100000);
        return String.valueOf(otp);
    }
}
