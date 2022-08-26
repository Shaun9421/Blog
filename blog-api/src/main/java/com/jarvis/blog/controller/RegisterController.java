package com.jarvis.blog.controller;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.params.LoginParam;
import com.jarvis.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam) {
        //sso 单点登录 后期吧登录注册分离出去 （单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);

    }
}
