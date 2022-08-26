package com.jarvis.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jarvis.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

//    List<Permission> findPermissionsByAdminId(Long adminId);
@Select("select * from blog_permission where id in (select permission_id from blog_admin_permission where admin_id=#{adminId})")
List<Permission> findPermissionsByAdminId(Long adminid);
}
