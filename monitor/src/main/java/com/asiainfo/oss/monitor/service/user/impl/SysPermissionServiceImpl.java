package com.asiainfo.oss.monitor.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.asiainfo.oss.monitor.mapper.user.SysPermissionMapper;
import com.asiainfo.oss.monitor.service.user.ISysPermissionService;
import com.asiainfo.oss.monitor.uitl.TreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
