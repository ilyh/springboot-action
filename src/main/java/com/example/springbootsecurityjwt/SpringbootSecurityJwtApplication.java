package com.example.springbootsecurityjwt;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan(basePackages="com.example.springbootsecurityjwt.mapper")
@RestController
public class SpringbootSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityJwtApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
