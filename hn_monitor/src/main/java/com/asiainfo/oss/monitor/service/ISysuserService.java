package com.asiainfo.oss.monitor.service;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.entity.Sysuser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-12
 */
public interface ISysuserService extends IService<Sysuser> {

    public  Sysuser findByUserName(String userName);

    public void saveUser(Sysuser user);

    public IPage<Sysuser> getAllUsers(Page<Sysuser> page);

    public Long getUserId(String username);

    public JsonData deleteUserById(Integer id, String delete_user_role);


}
