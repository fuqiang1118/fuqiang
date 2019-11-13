package com.asiainfo.oss.monitor.controller;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.entity.Sysuser;
import com.asiainfo.oss.monitor.entity.UserRole;
import com.asiainfo.oss.monitor.service.ISysuserService;
import com.asiainfo.oss.monitor.service.IUserRoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by fuqiang on 2019/11/11.
 *
 * 注册视图
 */

@Controller
public class RegisterController {

    @Resource
    private ISysuserService userService;

    @Resource
    private IUserRoleService userRoleService;

    private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @RequestMapping(value ="/register" )
    @ResponseBody
    @Transactional
    public JsonData register(Sysuser user){
        String rawPasswod = user.getPassword();
        String encodePassword = ENCODER.encode(rawPasswod);
        user.setPassword(encodePassword);
        userService.saveUser(user);
        long userID = userService.getUserId(user.getUsername());
        long roleID = 2l;//每个新注册用户默认设置角色为普通用户
        UserRole userAndRole = new UserRole(userID,roleID);
        userRoleService.save(userAndRole);
        return new JsonData(200,"注册成功");
    }

}
