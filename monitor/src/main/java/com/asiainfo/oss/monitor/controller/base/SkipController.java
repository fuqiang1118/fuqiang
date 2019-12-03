package com.asiainfo.oss.monitor.controller.base;

import com.asiainfo.oss.monitor.entity.user.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: monitor
 * @description: 跳转页面Conrtoller
 * @author: fuqiang
 * @date: 2019-11-18 10:10
 **/
@Controller
@RequestMapping("skip")
public class SkipController {

    @RequestMapping(value="/getPage")
    public ModelAndView getPage(ModelAndView modelAndView, String pageName){
        modelAndView.setViewName(pageName);
        return modelAndView;
    }

}
