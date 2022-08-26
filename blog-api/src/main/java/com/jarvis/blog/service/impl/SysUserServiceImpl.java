package com.jarvis.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jarvis.blog.VO.ErrorCode;
import com.jarvis.blog.VO.LoginUserVo;
import com.jarvis.blog.VO.Result;
import com.jarvis.blog.VO.UserVo;
import com.jarvis.blog.dao.mapper.SysUserMapper;
import com.jarvis.blog.dao.pojo.SysUser;
import com.jarvis.blog.service.LoginService;
import com.jarvis.blog.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional //确保事务操作
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LoginService loginService; //loginService中校验token是否存在

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);//防止空指针
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("jarvis"); //如果没有便设定为用户名为jarvis
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));

        return userVo;
    }




    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);//防止空指针
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("jarvis"); //如果没有便设定为用户名为jarvis
        }
        return sysUserMapper.selectById(id);
    }


    /**
     * 查询用户
     *
     * @param account
     * @param password
     * @return
     */

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        //account id 头像 名称
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAdmin, SysUser::getNickname ,SysUser::getAvatar);
        //增加查询效率，只查询一条
        queryWrapper.last("limit 1");
//selectOne的坑https://www.guangmuhua.com/articleDetail/2625
        return sysUserMapper.selectOne(queryWrapper);
    }


    /**
     * 1.token的合法性校验
     *  是否为空 解析是否成功 redis是否合法
     * 2. 如果校验失败，返回错误
     * 3.如果成功，返回对应结果
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1、token合法性校验
         * 是否为空 ，解析是否成功，redis是否存在
         * 2、如果校验失败，返回错误
         *3、如果成功，返回对应结果 LoginUserVo
         */

        //去loginservice中去校验token
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);

    }

    //查找用户
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //id自动生成 生成的是分布式id  雪花算法
        //Mybatisplus  中默认的id 是 @TableId(type= IdType.ASSIGN_ID) 类型
        //自增id，如果用户多了需要分表
        this.sysUserMapper.insert(sysUser);
    }


}
