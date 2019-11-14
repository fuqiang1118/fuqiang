package com.asiainfo.oss.monitor.dao;

import com.asiainfo.oss.monitor.entity.Sysuser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-12
 */

public interface SysuserMapper extends BaseMapper<Sysuser> {
    //根据用户名查找用户信息
    public Sysuser findByUserName(String username);

    //查找所有用户信息
    public List<Sysuser> getAllUsers();

}
