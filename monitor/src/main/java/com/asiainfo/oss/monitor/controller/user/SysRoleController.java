package com.asiainfo.oss.monitor.controller.user;


import com.asiainfo.oss.monitor.base.result.ResponseCode;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.dto.RoleDto;
import com.asiainfo.oss.monitor.entity.user.SysRole;
import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.asiainfo.oss.monitor.service.user.ISysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("role")
@Slf4j
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    /**
     * 跳转角色新增页
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("sysRole",new SysRole());
        return "role/role-add";
    }

    @GetMapping(value = "/edit")
    public String editRole(Model model, SysRole role) {
        model.addAttribute("sysRole",roleService.getById(role.getId()));
        return "role/role-edit";
    }

    @RequestMapping("/getAllRole")
    @ResponseBody
    public Results<SysRole> getAllRole(){
        List<SysRole> roleList = roleService.list();
        return Results.success(roleList.size(),roleList);
    }

    /**
     * 获取角色列表分页列表
     * @param currentPage
     * @param limit
     * @return
     */
    @GetMapping("/getRolerList")
    @ResponseBody
    public Results<SysRole> getRolerList(int currentPage, int limit){
        log.info("SysRoleController.getRolerList----params:currentPage:" + currentPage + ";limit:" + limit);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        Page<SysRole> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        //startPage后紧跟的这个查询就是分页查询
        IPage<SysRole> pageRoles = roleService.getAllRoles(page);
        log.debug("SysRoleController.getRolerList----Success!results:pageNum:"+pageRoles.getRecords().size());
        return  Results.success(new Long(pageRoles.getTotal()).intValue(),pageRoles.getRecords());
    }

    /**
     * 新增角色账号
     * @param roleDto
     * @return
     */
    @PostMapping("/addRole")
    @ResponseBody
    public Results addRole(@RequestBody RoleDto roleDto){
        log.info("SysRoleController.addRole----params：roleDto:" + roleDto.toString());
        roleDto.setCreatetime(new Date());
        roleDto.setUpdatetime(new Date());
        boolean result = roleService.saveRole(roleDto);
        log.debug(roleDto.getName() + "SysUserController.addRole----success!results:" + result);
        if (result){
            return Results.success();
        }else
            return Results.failure();
    }

    /**
     * 修改角色账号
     * @param roleDto
     * @return
     */
    @PostMapping(value = "/editRole")
    @ResponseBody
    public Results updateRole(@RequestBody RoleDto roleDto) {
        log.info("SysRoleController.updateRole----params：roleDto:" + roleDto.toString());
        return roleService.updateRole(roleDto) ? Results.success() : Results.failure();
    }

    /**
     * 删除角色
     * @param roleDto
     * @return
     */
    @GetMapping(value = "/delete")
    @ResponseBody
    public Results<SysRole> deleteRole(RoleDto roleDto) {
        log.info("SysRoleController.deleteRole----params：roleDto:" + roleDto.toString());
        return roleService.deleteRole(roleDto.getId()) ?
                Results.success() : Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(),ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

    /**
     * 批量删除管理员
     * @param ids
     * @return
     */
    @PostMapping("/deleteAll")
    @ResponseBody
    public Results deleteAllUser(Integer[] ids){
        log.info("SysRoleController.deleteAllUser----params：ids:" + ids);
        StringBuilder result = roleService.deleteAllUserById(ids);
        log.debug(" SysRoleController.deleteAllUser----success!results:" + result);
        if (result==null || result.length()==0){
            return Results.success();
        }else
            return Results.failure(205,result.append("角色存在用户关联，无法删除").toString());
    }

    /**
     * 根据角色名模糊查询角色分页列表
     * @param currentPage
     * @param limit
     * @param roleName
     * @return
     */
    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    public Results findRoleByFuzzyRoleName(int currentPage, int limit, String roleName) {
        log.info("SysRoleController.findRoleByFuzzyRoleName----params:currentPage:" + currentPage + ";limit:" + limit + ";roleName:" + roleName);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        Page<SysRole> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        //startPage后紧跟的这个查询就是分页查询
        IPage<SysRole> pageRoles = roleService.findRoleByFuzzyRoleName(roleName,page);
        log.debug("SysRoleController.findRoleByFuzzyRoleName----Success!results:pageNum:"+pageRoles.getRecords().size());
        return  Results.success(new Long(pageRoles.getTotal()).intValue(),pageRoles.getRecords());
    }

    String pattern = "yyyy-MM-dd";
    //只需要加上下面这段即可，注意不能忘记注解
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
    }

}
