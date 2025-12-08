package com.example.service;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private OtpService otpService;

    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");

            //return "Tên đăng nhập đã tồn tại!";
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại!");

            //return "Email đã tồn tại!";
        }

        user.setEnabled(false);
        userRepository.save(user);

        String otp = otpService.generateOtp(user.getEmail());
        otpService.sendOtpEmail(user.getEmail(), otp);

        return "Đăng ký thành công! Vui lòng kiểm tra email để nhập OTP.";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return "Tên đăng nhập không tồn tại!";
        if (!user.getPassword().equals(password)) return "Mật khẩu không đúng!";
        if (!user.isEnabled()) return "Tài khoản chưa xác thực email!";
        return "Đăng nhập thành công!";
    }
}
