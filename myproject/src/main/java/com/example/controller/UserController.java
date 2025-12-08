package com.example.controller;

import com.example.model.User;
import com.example.service.OtpService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String msg = userService.register(user);
            return ResponseEntity.ok(msg);  // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(""+ e.getMessage()); // 500
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String otp = data.get("otp");

        boolean verified = otpService.verifyOtp(email, otp);
        if (verified) {
            // kích hoạt user
            User user = userService.userRepository.findByEmail(email);
            if (user != null) {
                user.setEnabled(true);
                userService.userRepository.save(user);
            }
            return ResponseEntity.ok("Xác thực OTP thành công!");
        } else {
            return ResponseEntity.ok("OTP không hợp lệ hoặc đã hết hạn!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String result = userService.login(username, password);
        return ResponseEntity.ok(result);
    }
}
