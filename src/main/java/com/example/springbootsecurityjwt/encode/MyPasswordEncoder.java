package com.example.springbootsecurityjwt.encode;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName MyPasswordEncode
 * @Description TODO
 * @createTime 2023/11/28
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
