package com.asiainfo.oss.monitor.controller;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.service.ISysuserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by fuqiang on 2019/11/11.
 *
 * 用户处理视图
 */

@Controller
@Slf4j
public class UserController {

    @Resource
    private ISysuserService userService;

    @RequestMapping(value = "/deleteUserById")
    @ResponseBody
    public JsonData deleteUserById(long id,String role){
        return userService.deleteUserById(id,role);
    }

}
