package com.jarvis.blog.handler;

import com.jarvis.blog.VO.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //对加了controller注解的方法进行拦截处理  Aop的实现
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据的
    public Result doException(Exception exception) {
        exception.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
