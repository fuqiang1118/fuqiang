package com.asiainfo.oss.monitor.service;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-12
 */
public interface IUserRoleService extends IService<UserRole> {

    public boolean  save(UserRole userAndRole);

    public void setUserAdmin(long userID);

    public JsonData setUser(long userID);

}
