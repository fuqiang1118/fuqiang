package com.asiainfo.oss.monitor.service.user.impl;

import com.asiainfo.oss.monitor.base.result.ResponseCode;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.dto.RoleDto;
import com.asiainfo.oss.monitor.entity.user.SysRole;
import com.asiainfo.oss.monitor.entity.user.SysRolePermission;
import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.asiainfo.oss.monitor.mapper.user.SysRoleMapper;
import com.asiainfo.oss.monitor.mapper.user.SysRolePermissionMapper;
import com.asiainfo.oss.monitor.mapper.user.SysRoleUserMapper;
import com.asiainfo.oss.monitor.service.user.ISysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;
    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Override
    public IPage<SysRole> getAllRoles(Page<SysRole> page) {
        return roleMapper.selectPage(page,null);
    }

    @Override
    public boolean saveRole(RoleDto roleDto) {
        int insertRole = roleMapper.insert(roleDto);
        List<Long> permissionIds = roleDto.getPermissionIds();
        //移除0,permission id是从1开始
        permissionIds.remove(0L);
        //2、保存角色对应的所有权限
        if (!CollectionUtils.isEmpty(permissionIds)) {
            for (Long permissionId : permissionIds){
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleDto.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
        return true;
    }

    @Override
    public boolean updateRole(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);
        //1、更新角色权限之前要删除该角色之前的所有权限
        QueryWrapper<SysRolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLEID",roleDto.getId());
        rolePermissionMapper.delete(wrapper);

        //2、判断该角色是否有赋予权限值，有就添加"
        if (!CollectionUtils.isEmpty(permissionIds)) {
            for (Long permissionId : permissionIds){
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleDto.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }

        //3、更新角色表
        int countData = roleMapper.updateById(roleDto);

        if(countData > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteRole(Long roleId) {
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLEID",roleId);
        List<SysRoleUser> datas = roleUserMapper.selectList(wrapper);
        if(datas.size() <= 0){
            roleMapper.deleteById(roleId);
            return true;
        }
        return false;
    }

    @Override
    public IPage<SysRole> findRoleByFuzzyRoleName(String roleName, Page<SysRole> page) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.like("NAME",roleName);
        return roleMapper.selectPage(page,wrapper);
    }

    @Override
    public StringBuilder deleteAllUserById(Integer[] ids) {

        StringBuilder failRoleIds = new StringBuilder();
        for (Integer roleid : ids){
            boolean result = deleteRole(roleid.longValue());
            if (!result){
                failRoleIds.append("roleId:").append(roleid).append(",");
            }
        }

        return failRoleIds;
    }
}
