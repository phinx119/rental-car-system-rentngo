package com.group3.rentngo.controller;

import com.group3.rentngo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage() {
        return "home-page";
    }

    @GetMapping("/hello")
    public String hello() {
        return "login";
    }
}
