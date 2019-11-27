package com.asiainfo.oss.monitor.controller.user;


import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysRole;
import com.asiainfo.oss.monitor.service.user.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@RestController
@RequestMapping("role")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    @RequestMapping("/getAllRole")
    public Results<SysRole> getAllRole(){
        List<SysRole> roleList = roleService.list();
        return Results.success(roleList.size(),roleList);
    }
}
