package com.example.springbootsecurityjwt.service;

import com.example.springbootsecurityjwt.domain.User;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName AuthService
 * @Description TODO
 * @createTime 2023/11/6
 */
public interface AuthService {
    Integer register(User userToAdd );
    String login( String username, String password );
}
