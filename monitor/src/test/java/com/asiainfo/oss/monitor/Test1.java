package com.asiainfo.oss.monitor;

import com.asiainfo.oss.monitor.entity.SysUser;
import com.asiainfo.oss.monitor.mapper.SysUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;


/**
 * @program: monitor
 * @description: 测试类
 * @author: fuqiang
 * @date: 2019-11-14 15:49
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void insertuserid(){
        SysUser user = new SysUser();
        user.setUsername("fuqiang");
        user.setPassword("fuqiang");
        user.setCreatetime(LocalDateTime.now());
        user.setUpdatetime(LocalDateTime.now());
        int insert = userMapper.insert(user);
        System.out.println("插入："+insert+"条数据！");
    }

}
