package com.asiainfo.oss.monitor.service.user;

import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface ISysRoleUserService extends IService<SysRoleUser> {

    /**
     * 根据用户id查询角色信息
     * @param userId
     * @return
     */
    SysRoleUser getByUserId(Integer userId);
}
