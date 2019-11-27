package com.asiainfo.oss.monitor.service.user.impl;

import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.asiainfo.oss.monitor.mapper.user.SysRoleUserMapper;
import com.asiainfo.oss.monitor.service.user.ISysRoleUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements ISysRoleUserService {

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Override
    public SysRoleUser getByUserId(Integer userId) {
        QueryWrapper<SysRoleUser> wrapper = new QueryWrapper<>();
        wrapper.eq("USERID",userId);
        return roleUserMapper.selectOne(wrapper);
    }
}
