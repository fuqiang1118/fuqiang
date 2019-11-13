package com.asiainfo.oss.monitor.controller;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.entity.Sysuser;
import com.asiainfo.oss.monitor.service.ISysuserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by fuqiang on 2019/11/11.
 *
 * 校验处理视图
 */

@Controller
@Slf4j
public class ValidationController {

    @Resource
    private ISysuserService userService;


    @RequestMapping(value = "/checkNameIsExistOrNot")
    @ResponseBody
    public JsonData checkUserNameISExistOrNot(String username){
        Sysuser user = userService.findByUserName(username);
//        log.info("{}",user+"******");
        if (user != null){
            return new JsonData(301,"用户名被占有了");

        } else {
            return new JsonData(200,"用户名可以使用");
        }
    }



}
