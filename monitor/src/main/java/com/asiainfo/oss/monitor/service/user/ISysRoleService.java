package com.asiainfo.oss.monitor.service.user;

import com.asiainfo.oss.monitor.dto.RoleDto;
import com.asiainfo.oss.monitor.entity.user.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取角色列表，分页展示
     * @param page
     * @return
     */
    IPage<SysRole> getAllRoles(Page<SysRole> page);

    /**
     * 新增角色
     * @param roleDto
     * @return
     */
    boolean saveRole(RoleDto roleDto);

    /**
     * 修改角色
     * @param roleDto
     * @return
     */
    boolean updateRole(RoleDto roleDto);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean deleteRole(Long roleId);

    /**
     * 根据角色名模糊查询角色分页列表
     * @param roleName
     * @param page
     * @return
     */
    IPage<SysRole> findRoleByFuzzyRoleName(String roleName, Page<SysRole> page);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    StringBuilder deleteAllUserById(Integer[] ids);
}
