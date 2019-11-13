package com.asiainfo.oss.monitor.controller;

import com.asiainfo.oss.monitor.entity.Sysuser;
import com.asiainfo.oss.monitor.service.ISysuserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuqiang on 2019/11/11.
 *
 * 分页处理视图
 */

@Controller
@Slf4j
public class PageController {

    @Resource
    private ISysuserService userService;

    //用户资料分页
    @RequestMapping(value = "/getAllUser")
    @ResponseBody
    public Map<String,Object> page(int currentPage,int limit){
        //引入分页查询，使用PageHelper分页功能在查询之前传入当前页，然后多少记录
//        log.info("{}","page");
        Page<Sysuser> page = new Page<>();
        page.setSize(limit);
        page.setCurrent(currentPage);
        //startPage后紧跟的这个查询就是分页查询
        IPage<Sysuser> PageUsers = userService.getAllUsers(page);
        for(Sysuser user : PageUsers.getRecords()){
            //user.setRole(user.getRoles().get(0).getName());//用户角色包装 方便处理
        }
        Map<String,Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        data.put("count", PageUsers.getTotal());
        //将分页后的数据返回（每页要显示的数据）
        data.put("data", PageUsers.getRecords());
        //返回给前端
        return data;

    }

}
