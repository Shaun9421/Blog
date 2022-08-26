package com.jarvis.blog.handler;

import com.alibaba.fastjson.JSON;
import com.jarvis.blog.VO.ErrorCode;
import com.jarvis.blog.VO.Result;
import com.jarvis.blog.dao.pojo.SysUser;
import com.jarvis.blog.service.LoginService;
import com.jarvis.blog.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired

    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在方法之前进行执行
        /**
         * 1. 需要判断 请求的接口路径是否为HandlerMethod controller方法
         * 2. 判断token是否为空 为空未登录
         * 3. 如果token不为空 登录验证 loginService checkToken
         * 4. 如果认证成功 放行即可
         */
        if (!(handler instanceof HandlerMethod)) {
            //如果不是handler方法直接放行
            //handler可能是 RequestResourceHandler springboot 程序访问静态资源 默认去classpath下的static目录去查询
            return true;
        }
        //获取token并判断是否为空
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");//浏览器识别
            response.getWriter().println(JSON.toJSONString(result));
            return false;

        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(JSON.toJSONString(result));
            return false;
        }
        //登录验证成功，放行
        //controller中获取用户信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal 中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
