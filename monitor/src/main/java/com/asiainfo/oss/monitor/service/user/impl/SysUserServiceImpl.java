package com.asiainfo.oss.monitor.service.user.impl;

import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysRole;
import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.asiainfo.oss.monitor.mapper.user.SysRoleMapper;
import com.asiainfo.oss.monitor.mapper.user.SysRoleUserMapper;
import com.asiainfo.oss.monitor.mapper.user.SysUserMapper;
import com.asiainfo.oss.monitor.service.user.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.Arrays;
import java.util.Collections;

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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Override
    public IPage getAllUsers(Page page) {
        return userMapper.selectPage(page,null);
    }

    @Override
    public boolean save(SysUser user, Integer roleId) {
        int insertUser = userMapper.insert(user);
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleid(roleId.longValue());
        sysRoleUser.setUserid(user.getId());
        int insertRole = roleUserMapper.insert(sysRoleUser);
        return (insertUser > 0 && insertRole > 0) ? true : false;
    }

    @Override
    public boolean updateUser(SysUser user, Integer roleId) {
        int updateUser = userMapper.updateById(user);
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USERID",user.getId());
        SysRoleUser userRole = new SysRoleUser();
        userRole.setRoleid(roleId.longValue());
        int updateRoleUser = roleUserMapper.update(userRole, wrapper);
        return (updateUser > 0 && updateRoleUser > 0) ? true : false;
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        int deleteUser = userMapper.deleteById(userId);
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USERID",userId);
        int deleteRoleUser = roleUserMapper.delete(wrapper);
        return (deleteUser > 0 && deleteRoleUser > 0) ? true : false;
    }

    @Override
    public IPage findUserByFuzzyUserName(String username, Page<SysUser> page) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like("USERNAME",username);
        return userMapper.selectPage(page,wrapper);
    }

    @Override
    public boolean deleteAllUserById(Integer[] ids) {
        int size = ids.length;
        if(size < 1){
            return false;
        }

        int delUserNum = userMapper.deleteBatchIds(Arrays.asList(ids));
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        wrapper.in("USERID",Arrays.asList(ids));
        int deleteRoleUserNum = roleUserMapper.delete(wrapper);
        return (delUserNum >= size && deleteRoleUserNum >= size) ? true : false;
    }

    @Override
    public SysUser getUserByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USERNAME",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("PHONE",phone);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("EMAIL",email);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USERNAME",username);
        SysUser u =  userMapper.selectOne(wrapper);
        if (u == null) {
            return Results.failure(1,"用户不存在");
        }

        if (!new BCryptPasswordEncoder().matches(oldPassword,u.getPassword())) {
            return Results.failure(1,"旧密码错误");
        }
        userMapper.changePassword(u.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }
}
