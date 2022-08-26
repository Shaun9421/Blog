package com.jarvis.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jarvis.blog.admin.mapper.PermissionMapper;
import com.jarvis.blog.admin.model.params.PageParam;
import com.jarvis.blog.admin.pojo.Permission;
import com.jarvis.blog.admin.vo.PageResult;
import com.jarvis.blog.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    /**
     * 要的数据  表的字段 permission
     *分页查询 list total
     * @param pageParam
     * @return
     */
    public Result listPermissions(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNoneBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page,queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setTotal(permissionPage.getTotal());
        pageResult.setList(permissionPage.getRecords());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
