package com.example.springbootsecurityjwt.service.impl;

import com.example.springbootsecurityjwt.domain.User;
import com.example.springbootsecurityjwt.mapper.UserMapper;
import com.example.springbootsecurityjwt.service.AuthService;
import com.example.springbootsecurityjwt.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private JwtTokenUtil jwtTokenUtil;
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
        return jwtTokenUtil.generateToken(username);
    }
}
