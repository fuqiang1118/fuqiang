package com.asiainfo.oss.monitor.controller.user;


import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.dto.RoleDto;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.asiainfo.oss.monitor.service.user.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 跳转到菜单权限新增页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public String addPermission(Model model) {
        model.addAttribute("sysPermission",new SysPermission());
        return "permission/permission-add";
    }

    /**
     * 跳转到菜单权限修改页面
     * @param model
     * @param permission
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public String editPermission(Model model, SysPermission permission) {
        model.addAttribute("sysPermission",permissionService.getSysPermissionById(permission.getId()));
        return "permission/permission-add";
    }

    /**
     * 获取所有权限列表
     * @return
     */
    @GetMapping("/listAllPermission")
    @ResponseBody
    public Results<JSONArray> listAllPermission() {
        log.info("SysPermissionController.listAllPermission");
        JSONArray allPermission = permissionService.getAllPermission();
        log.debug("SysPermissionController.listAllPermission----success:" + allPermission);
        return Results.success(allPermission);
    }

    /**
     * 根据角色id获取权限列表
     * @param roleDto
     * @return
     */
    @RequestMapping(value = "/listAllPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        log.info("SysPermissionController.listAllPermissionByRoleId----params:RoleDto:" + roleDto);
        return Results.success(0,permissionService.getPermissionByRoleId(roleDto.getId().intValue()));
    }

    /**
     * 获取所有菜单
     * @return
     */
    @GetMapping("/menuAll")
    @ResponseBody
    public Results getMenuAll(){
        log.info("SysPermissionController.getMenuAll");
        return Results.success(0,permissionService.getMenuAll());
    }

    /**
     * 添加一个新菜单权限
     * @param permission
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public Results<SysPermission> savePermission(@RequestBody SysPermission permission) {
        log.info("SysPermissionController.savePermission----params:SysPermission" + permission);
        boolean result = permissionService.save(permission);
        return result ? Results.success() : Results.failure();
    }

    /**
     * 根据菜单权限id更新菜单信息
     * @param permission
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public Results updatePermission(@RequestBody  SysPermission permission) {
        log.info("SysPermissionController.updatePermission----params:SysPermission" + permission);
        boolean result = permissionService.updateById(permission);
        return result ? Results.success() : Results.failure();
    }

    /**
     * 删除权限菜单
     * @param sysPermission
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:menu:del')")
    public Results deletePermission(SysPermission sysPermission) {
        log.info("SysPermissionController.deletePermission----params:SysPermission" + sysPermission);
        boolean result = permissionService.deletePermission(sysPermission.getId());
        return result ? Results.success() : Results.failure();
    }

    /**
     * 根据用户id获取菜单
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public Results<JSONArray> getMenu(Long userId) {
        log.info("SysPermissionController.getMenu----params:userId" + userId);
        JSONArray allPermission = permissionService.getMenu(userId);
        log.debug("SysPermissionController.getMenu----success:" + allPermission);
        return Results.success(allPermission);
    }
}
