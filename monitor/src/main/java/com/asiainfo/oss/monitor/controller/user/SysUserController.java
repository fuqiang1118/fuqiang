package com.asiainfo.oss.monitor.controller.user;


import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.asiainfo.oss.monitor.service.user.ISysUserService;
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
     * @param user
     * @param roleId
     * @return
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Results addUser(SysUser user, Integer roleId){
        log.info("SysUserController.addUser----params：user:" + user.toString() + "; roleId:" + roleId);
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        user.setStatus("1");
        //String enpasswd = ENCODER.encode(user.getPassword());
        //user.setPassword(enpasswd);
        boolean result = userService.save(user, roleId);
        log.debug(user.getUsername() + "SysUserController.addUser----success!results:" + result);
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

    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }
}
