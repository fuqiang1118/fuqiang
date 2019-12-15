package com.asiainfo.oss.monitor.controller.user;


import com.asiainfo.oss.monitor.base.result.ResponseCode;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.dto.UserDto;
import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.asiainfo.oss.monitor.service.user.ISysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    //private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    /**
     * 跳转管理员新增页
     * @param model
     * @return
     */
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String addUser(Model model) {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }

    /**
     * 跳转管理员编辑页面
     * @param model
     * @param userId
     * @return
     */
    @GetMapping("/edit")
    public String editUser(Model model,Integer userId){
        SysUser user = userService.getById(userId);
        model.addAttribute(user);
        return "user/user-edit";
    }

    /**
     * 获取管理员列表分页列表
     * @param currentPage
     * @param limit
     * @return
     */
    @GetMapping("/getUserList")
    @ResponseBody
    public Results<SysUser> getUserList(int currentPage,int limit){
        log.info("SysUserController.getUserList----params:currentPage:" + currentPage + ";limit:" + limit);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        Page<SysUser> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        //startPage后紧跟的这个查询就是分页查询
        IPage<SysUser> pageUsers = userService.getAllUsers(page);
        log.debug("SysUserController.getUserList----Success!results:pageNum:"+pageUsers.getRecords().size());
        return  Results.success(new Long(pageUsers.getTotal()).intValue(),pageUsers.getRecords());
    }

    /**
     * 新增管理员账号
     * @param userDto
     * @param roleId
     * @return
     */
    @PostMapping("/addUser")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    public Results addUser(UserDto userDto, Integer roleId){
        log.info("SysUserController.addUser----params：userDto:" + userDto.toString() + "; roleId:" + roleId);
        SysUser sysUser = null;
        sysUser = userService.getUserByUserName(userDto.getUsername());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(),ResponseCode.USERNAME_REPEAT.getMessage());
        }
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(), ResponseCode.EMAIL_REPEAT.getMessage());
        }
        userDto.setCreatetime(new Date());
        userDto.setUpdatetime(new Date());
        userDto.setStatus(1);
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        boolean result = userService.save(userDto, roleId);
        log.debug(userDto.getUsername() + "SysUserController.addUser----success!results:" + result);
        if (result){
            return Results.success();
        }else
            return Results.failure();
    }

    /**
     * 修改管理员账号
     * @param user
     * @param roleId
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Results editUser(SysUser user, Integer roleId){
        log.info("SysUserController.editUser----params：user:" + user.toString() + "; roleId:" + roleId);
        boolean result = userService.updateUser(user, roleId);
        log.debug(user.getUsername() + "SysUserController.addUser----success!results:" + result);
        if (result){
            return Results.success();
        }else
            return Results.failure();
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public Results deleteUser(Integer id){
        log.info("SysUserController.deleteUser----params：userid:" + id);
        boolean result = userService.deleteUserById(id);
        log.debug(id + " SysUserController.addUser----success!results:" + result);
        if (result){
            return Results.success();
        }else
            return Results.failure();
    }

    /**
     * 批量删除管理员
     * @param ids
     * @return
     */
    @PostMapping("/deleteAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public Results deleteAllUser(Integer[] ids){
        log.info("SysUserController.deleteAllUser----params：ids:" + ids);
        boolean result = userService.deleteAllUserById(ids);
        log.debug(" SysUserController.deleteAllUser----success!results:" + result);
        if (result){
            return Results.success();
        }else
            return Results.failure();
    }

    /**
     * 根据用户名模糊查找管理员
     * @param username
     * @return
     */
    @GetMapping("/findUserByFuzzyUserName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    public Results findUserByFuzzyUserName(String username,int currentPage,int limit){
        log.info("SysUserController.findUserByFuzzyUserName----params：username:" + username + ";currentPage:" + currentPage +";limit:" + limit);
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
        Page<SysUser> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        //startPage后紧跟的这个查询就是分页查询
        IPage<SysUser> pageUsers = userService.findUserByFuzzyUserName(username,page);
        log.debug("SysUserController.findUserByFuzzyUserName----Success!results:pageNum:"+pageUsers.getRecords().size());
        return  Results.success(new Long(pageUsers.getTotal()).intValue(),pageUsers.getRecords());
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        log.info("SysUserController.changePassword----params：username:" + username + ";oldPassword:" + oldPassword +";newPassword:" + newPassword);
        return userService.changePassword(username, oldPassword, newPassword);
    }

    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
