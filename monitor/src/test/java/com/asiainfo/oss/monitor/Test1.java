package com.asiainfo.oss.monitor;

import ch.ethz.ssh2.Connection;
import com.asiainfo.oss.monitor.entity.SysUser;
import com.asiainfo.oss.monitor.mapper.SysUserMapper;
import com.asiainfo.oss.monitor.uitl.EmailService;
import com.asiainfo.oss.monitor.uitl.OSUtils;
import com.asiainfo.oss.monitor.uitl.RemoteCommandUtil;
import org.apache.velocity.runtime.directive.Foreach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private EmailService emailService;
    @Autowired
    private OSUtils osUtils;

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

    @Test
    public void sendMail(){
        emailService.sendSimpleEmail("1156517308@qq.com","测试邮件","测试成功！");
    }

    @Test
    public void connection(){
        RemoteCommandUtil.login("192.168.50.144","root","fuqiang22");
    }

    @Test
    public void exectext(){
        Connection conn = RemoteCommandUtil.login("192.168.50.144", "root", "fuqiang22");
        String execute = RemoteCommandUtil.execute(conn, "cat /proc/meminfo");
        System.out.println(execute);
    }

    @Test
    public void ostest() {

        List<Map> ipList = new ArrayList<>();
        Map<String, String> ipMap = new HashMap<>();
        ipMap.put("ip","192.168.50.144");
        ipMap.put("username","root");
        ipMap.put("password","fuqiang22");
        Map<String, String> ipMap1 = new HashMap<>();
        ipMap1.put("ip","192.168.50.145");
        ipMap1.put("username","root");
        ipMap1.put("password","fuqiang22");
        Map<String, String> ipMap2 = new HashMap<>();
        ipMap2.put("ip","192.168.50.146");
        ipMap2.put("username","root");
        ipMap2.put("password","fuqiang22");
        ipList.add(ipMap);
        ipList.add(ipMap1);
        ipList.add(ipMap2);

        StringBuffer cpuText = new StringBuffer();
        StringBuffer memText = new StringBuffer();
        boolean cpuSign = false;
        boolean memSign = false;
        for (Map map : ipList){
            osUtils.setIp(map.get("ip").toString());
            osUtils.setUsername(map.get("username").toString());
            osUtils.setPassword(map.get("password").toString());
            float cpuUsage = osUtils.cpuUsage();
            System.out.println(cpuUsage);
            if(cpuUsage > 10.0 ){
               cpuSign = true;
               cpuText.append(map.get("ip").toString()+"  CPU使用率为："+cpuUsage);
            }
            //内存使用情况
            long memoryUsage = osUtils.memoryUsage();
            if((memoryUsage/1024) < 10){
                memSign = true;
                memText.append(map.get("ip").toString()+"  内存使用率为："+memoryUsage);
            }
            System.out.println(memoryUsage);
        }

        if(cpuSign){
            emailService.sendSimpleEmail("1156517308@qq.com","服务器cpu使用率过高，请注意查看", "服务器提醒:"+cpuText.toString());
        }

        if(memSign){
            emailService.sendSimpleEmail("1156517308@qq.com","服务器cpu使用率过高，请注意查看", "服务器提醒:"+memText.toString());
        }


    }



}
