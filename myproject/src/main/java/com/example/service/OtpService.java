package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final int EXPIRE_MINUTES = 10;

    // Sinh OTP và lưu thẳng vào user
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));

        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setOtpCode(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(EXPIRE_MINUTES));
            userRepository.save(user);
        }

        return otp;
    }

    // Xác thực OTP từ DB
    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        if (user.getOtpCode() != null
                && user.getOtpCode().equals(otp)
                && LocalDateTime.now().isBefore(user.getOtpExpiry())) {
            // OTP đúng và còn hạn
            user.setEnabled(true);      // kích hoạt tài khoản
            user.setOtpCode(null);      // xóa OTP sau khi dùng
            user.setOtpExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Gửi email OTP
    public void sendOtpEmail(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Mã OTP xác thực tài khoản");
            message.setText("Xin chào,\n\nMã OTP của bạn là: " + otp +
                    "\nMã này sẽ hết hạn sau " + EXPIRE_MINUTES + " phút.\n\nTrân trọng!");

            mailSender.send(message);
            System.out.println("Đã gửi OTP đến email: " + email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

