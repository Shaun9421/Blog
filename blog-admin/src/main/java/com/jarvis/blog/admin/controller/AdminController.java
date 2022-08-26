package com.jarvis.blog.admin.controller;

import com.jarvis.blog.admin.model.params.PageParam;
import com.jarvis.blog.admin.pojo.Permission;
import com.jarvis.blog.admin.service.PermissionService;
import com.jarvis.blog.admin.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){

        return permissionService.listPermissions(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
