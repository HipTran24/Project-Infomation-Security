package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        // static HTML
        return "redirect:/login.html";

        // Nếu dùng Thymeleaf thì:
        // return "login";  // login.html trong templates
    }
}
