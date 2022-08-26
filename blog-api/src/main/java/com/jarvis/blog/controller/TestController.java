package com.jarvis.blog.controller;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.dao.pojo.SysUser;
import com.jarvis.blog.utils.UserThreadLocal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}