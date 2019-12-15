package com.asiainfo.oss.monitor.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.asiainfo.oss.monitor.mapper.user.SysPermissionMapper;
import com.asiainfo.oss.monitor.service.user.ISysPermissionService;
import com.asiainfo.oss.monitor.uitl.TreeUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@Service
@Transactional
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;
    @Override
    public JSONArray getAllPermission() {
        List<SysPermission> sysPermissions = permissionMapper.selectList(null);
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, sysPermissions, array);
        return array;
    }

    @Override
    public List<SysPermission> getPermissionByRoleId(Integer roleId) {
        return permissionMapper.getPermissionByRoleId(roleId);
    }

    @Override
    public List<SysPermission> getMenuAll() {
        return permissionMapper.selectList(null);
    }

    @Override
    public SysPermission getSysPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public boolean deletePermission(Long id) {
        /**删除自己*/
        int deleteNum = permissionMapper.deleteById(id);
        /**删除下面的所有的子类菜单*/
        int deletePNum = permissionMapper.deleteByParentId(id);
        return deleteNum > 0 ? true : false;
    }

    @Override
    public JSONArray getMenu(Long userId) {
        List<SysPermission> datas = permissionMapper.listMenuByUserId(userId);
        //去除按钮
        datas = datas.stream().filter(p -> p.getType().equals("1")).collect(Collectors.toList());

        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, datas, array);
        return array;
    }

}
