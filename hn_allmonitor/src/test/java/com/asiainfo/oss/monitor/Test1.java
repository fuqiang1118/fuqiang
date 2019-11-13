package com.asiainfo.oss.monitor;

import com.asiainfo.oss.monitor.dao.RoleMapper;
import com.asiainfo.oss.monitor.dao.SysuserMapper;
import com.asiainfo.oss.monitor.entity.Role;
import com.asiainfo.oss.monitor.entity.Sysuser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: hn_monitor
 * @description: 测试用例
 * @author: fuqiang
 * @date: 2019-11-13 10:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
    @Autowired
    private SysuserMapper sysuserMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void getRole(){
        List<Role> roles = roleMapper.selectList(null);
        for (Role role :roles){
            System.out.println(role);
        }
    }

    @Test
    public void saveSysUser(){
        Sysuser user = new Sysuser();
        user.setUsername("fuqiang");
        user.setPassword("fuqiang");
        int insert = sysuserMapper.insert(user);
        System.out.println("受影响的行数为："+insert);
    }
}
