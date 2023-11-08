package com.example.springbootsecurityjwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaonanGuo
 * @version 1.0.0
 * @ClassName TestController
 * @Description TODO
 * @createTime 2023/11/6
 */
@RestController
@RequestMapping("/authorize")
public class TestController {
    // 测试普通权限
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    @GetMapping("/normal/test")
    public String test1() {
        return "ROLE_NORMAL /normal/test接口调用成功！";
    }

    // 测试管理员权限
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping( value = "/admin/test", method = RequestMethod.GET )
    public String test2() {
        return "ROLE_ADMIN /admin/test接口调用成功！";
    }
}
