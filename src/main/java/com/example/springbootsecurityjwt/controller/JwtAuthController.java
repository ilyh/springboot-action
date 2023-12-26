package com.example.springbootsecurityjwt.controller;

import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName JwtAuthController
 * @Description TODO
 * @createTime 2023/11/6
 */
@RestController
public class JwtAuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/user/login")
    public String login(@RequestBody User user) {

        return authService.login(user.getUsername(), user.getPassword());
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/user/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/user/register")
    public String register(@RequestBody User user) {
        authService.register(user);
        return "success";
    }

    @GetMapping("/user/logout")
    public String logout() {
        return authService.logout();
    }
}
