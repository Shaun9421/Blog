package com.jarvis.blog.service;

import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.params.LoginParam;
import com.jarvis.blog.dao.pojo.SysUser;
import org.springframework.transaction.annotation.Transactional;

//@Transactional //实现类多个时添加到接口
public interface LoginService {
    /**
     * 登陆功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 校验token
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @return
     */
    Result logout(String token);

    /**
     * 注册接口
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}


