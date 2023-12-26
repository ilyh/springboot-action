package com.example.springbootsecurityjwt.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.mapper.UserMapper;
import com.example.springbootsecurityjwt.service.AuthService;


import com.example.springbootsecurityjwt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName AuthServiceImpl
 * @Description TODO
 * @createTime 2023/11/6
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Integer register(User user) {
        String password = user.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 加密密码
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        return userMapper.insertSelective(user);
    }

    @Override
    public String login(String username, String password) {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("验证失败");
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateToken(username);
        redisTemplate.opsForValue().set(customUserDetails.getUsername(), customUserDetails, 3600, TimeUnit.SECONDS);
        return token;
    }

    @Override
    public String logout() {
        // 获取当前认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 从认证信息中获取用户信息
        if (authentication != null && authentication.isAuthenticated()) {
            // 如果用户已认证，可以获取用户详细信息
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User userDetails = (User) principal;
                String username = userDetails.getUsername();
                redisTemplate.delete(username);
                return "logout success";
            }
        }
        return "logout fail";
    }
}
