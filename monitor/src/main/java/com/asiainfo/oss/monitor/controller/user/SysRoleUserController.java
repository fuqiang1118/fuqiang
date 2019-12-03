package com.asiainfo.oss.monitor.controller.user;


import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.asiainfo.oss.monitor.service.user.ISysRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/roleuser")
@Slf4j
public class SysRoleUserController {


    @Autowired
    private ISysRoleUserService roleUserService;

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    @PostMapping("/getRoleUserByUserId")
    public Results getRoleUserByUserId(Integer userId) {
        log.info("SysRoleUserController.getRoleUserByUserId----param = " + userId);
        SysRoleUser roleUser = roleUserService.getByUserId(userId);
        log.debug("SysRoleUserController.getRoleUserByUserId----success!result:" + roleUser);
        return Results.success(roleUser);
    }

}
