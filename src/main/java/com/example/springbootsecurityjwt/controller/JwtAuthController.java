package com.example.springbootsecurityjwt.controller;

import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName JwtAuthController
 * @Description TODO
 * @createTime 2023/11/6
 */
@RestController
@RequestMapping("/authentication")
public class JwtAuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam(value = "username" ) String username, @RequestParam(value = "password" ) String password) {
        return authService.login(username, password);

    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        authService.register(user);
        return "success";
    }
}
