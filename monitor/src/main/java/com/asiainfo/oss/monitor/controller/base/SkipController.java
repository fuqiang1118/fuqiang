package com.asiainfo.oss.monitor.controller.base;

import com.asiainfo.oss.monitor.entity.user.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: monitor
 * @description: 跳转页面Conrtoller
 * @author: fuqiang
 * @date: 2019-11-18 10:10
 **/
@Controller
public class SkipController {

    /**
     * 跳转管理员列表页
     * @param pageName
     * @return
     */
    @RequestMapping("user/{pageName}")
    public String gotoUserPage(@PathVariable String pageName){
        return "user/"+pageName;
    }
}
