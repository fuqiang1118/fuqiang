package com.asiainfo.oss.monitor.controller.user;


import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.dto.RoleDto;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.asiainfo.oss.monitor.service.user.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@Controller
@RequestMapping("permission")
@Slf4j
public class SysPermissionController {
    @Autowired
    private ISysPermissionService permissionService;


    @GetMapping("/listAllPermission")
    @ResponseBody
    public Results<JSONArray> listAllPermission() {
        JSONArray allPermission = permissionService.getAllPermission();
        log.info("SysPermissionController.listAllPermission----success:" + allPermission);
        return Results.success(allPermission);
    }

    @RequestMapping(value = "/listAllPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        log.info("SysPermissionController.listAllPermissionByRoleId----params:RoleDto:" + roleDto);
        return Results.success(0,permissionService.getPermissionByRoleId(roleDto.getId().intValue()));
    }
}
