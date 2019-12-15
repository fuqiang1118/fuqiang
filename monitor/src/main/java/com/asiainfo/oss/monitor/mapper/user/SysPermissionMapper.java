package com.asiainfo.oss.monitor.mapper.user;

import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<SysPermission> getPermissionByRoleId(Integer roleId);

    @Delete("delete from sys_permission where parentId = #{parentId}")
    int deleteByParentId(Long parentId);


    @Select("SELECT DISTINCT sp.*  " +
            "FROM sys_role_user sru " +
            "INNER JOIN sys_role_permission srp ON srp.roleId = sru.roleId " +
            "LEFT JOIN sys_permission sp ON srp.permissionId = sp.id " +
            "WHERE " +
            "sru.userId = #{userId}")
    List<SysPermission> listMenuByUserId(@Param("userId") Long userId);
}
