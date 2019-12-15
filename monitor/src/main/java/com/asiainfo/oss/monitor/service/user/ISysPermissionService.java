package com.asiainfo.oss.monitor.service.user;

import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 获取所有权限
     * @return
     */
    JSONArray getAllPermission();

    /**
     * 根据角色id获取权限列表
     * @param roleId
     * @return
     */
    List<SysPermission> getPermissionByRoleId(Integer roleId);

    /**
     * 获取所有菜单
     * @return
     */
    List<SysPermission> getMenuAll();

    /**
     * 根据权限ID获取菜单权限信息
     * @param id
     * @return
     */
    SysPermission getSysPermissionById(Long id);

    /**
     * 删除菜单权限
     * @param id
     * @return
     */
    boolean deletePermission(Long id);

    /**
     * 根据用户id获取菜单
     * @param userId
     * @return
     */
    JSONArray getMenu(Long userId);
}
