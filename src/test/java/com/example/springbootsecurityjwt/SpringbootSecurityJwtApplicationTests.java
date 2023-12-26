package com.example.springbootsecurityjwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringbootSecurityJwtApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }

    @Test
    void testRedis() {
        final Object o = redisTemplate.opsForValue().get("zhangsan");
        System.out.println(o);
    }

}
