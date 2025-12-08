package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})  // quét toàn bộ com.example.*
public class MyprojectApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
	}
}
