package com.asiainfo.oss.monitor.service.user;

import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 分页获取所有用户
     * @param page
     * @return
     */
    IPage getAllUsers(Page page);

    /**
     * 保存一个用户信息
     * @param user
     * @param roleId
     * @return
     */
    boolean save(SysUser user,Integer roleId);

    /**
     * 修改用户信息
     * @param user
     * @param roleId
     * @return
     */
    boolean updateUser(SysUser user, Integer roleId);

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    boolean deleteUserById(Integer userId);

    /**
     * 根据用户名查找用户信息
     * @param username
     * @param page
     * @return
     */
    IPage findUserByFuzzyUserName(String username, Page<SysUser> page);

    /**
     * 根据用户id批量删除用户
     * @param ids
     * @return
     */
    boolean deleteAllUserById(Integer[] ids);
}
