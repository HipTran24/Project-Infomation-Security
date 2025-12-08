package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data   // tự sinh getter, setter, toString, equals, hashCode
@NoArgsConstructor  // constructor rỗng
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Column(nullable = false)
    private boolean enabled = false;
    private String otpCode;      // mã OTP
    private LocalDateTime otpExpiry; // thời gian hết hạn OTP (vd: 5 phút)

}

