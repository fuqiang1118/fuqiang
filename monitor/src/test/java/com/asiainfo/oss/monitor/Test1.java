package com.asiainfo.oss.monitor;

import ch.ethz.ssh2.Connection;
import com.asiainfo.oss.monitor.controller.user.SysUserController;
import com.asiainfo.oss.monitor.dto.UserDto;
import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.asiainfo.oss.monitor.mapper.user.SysRoleUserMapper;
import com.asiainfo.oss.monitor.mapper.user.SysUserMapper;
import com.asiainfo.oss.monitor.service.user.ISysUserService;
import com.asiainfo.oss.monitor.uitl.EmailService;
import com.asiainfo.oss.monitor.uitl.OSService;
import com.asiainfo.oss.monitor.uitl.RemoteCommandUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


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
    private OSService osUtils;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysRoleUserMapper roleUserMapper;

    private SysUserController sysUserController;

    @Test
    public void insertuserid(){
        SysUser user = new SysUser();
        user.setUsername("fuqiang");
        user.setPassword("fuqiang");
        user.setCreatetime(new Date());
        user.setUpdatetime(new Date());
        int insert = userMapper.insert(user);
        System.out.println("插入："+insert+"条数据！");
    }

    @Test
    public void sendMail(){
        emailService.sendSimpleEmail(new String[]{"1156517308@qq.com"},"测试邮件","测试成功！");
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

        StringBuffer text = new StringBuffer();

        boolean sign = false;
        for (Map map : ipList){
            Connection conn = RemoteCommandUtil.login(map.get("ip").toString(), map.get("username").toString(), map.get("password").toString());
            float cpuUsage = osUtils.cpuUsage(conn);
            System.out.println(cpuUsage);
            if(cpuUsage > 0.001 ){
               sign = true;
               text.append("\t" + map.get("ip").toString()+"  CPU使用率为：" + cpuUsage + "\r\n");
            }
            //内存使用情况
            long memoryUsage = osUtils.memoryUsage(conn);
            if((memoryUsage/1024) < 500){
                sign = true;
                text.append("\t" + map.get("ip").toString()+"  内存使用率为："+memoryUsage + "\r\n");
            }
            System.out.println(memoryUsage);
            RemoteCommandUtil.closeConnection(conn);
        }

        if(sign){
            emailService.sendSimpleEmail(new String[]{"1156517308@qq.com","wangkj3@asiainfo.com"},"服务器使用率过高，请注意查看", "服务器提醒:" + "\r\n" + text.toString());
        }


    }

    @Test
    public void saveUser(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("fuqiang");
        sysUser.setNickname("fuqiang");
        sysUser.setPassword(new BCryptPasswordEncoder().encode("fuqiang22"));
        sysUser.setEmail("fuqiang@qq.com");
        sysUser.setBirthday(new Date());
        sysUser.setCreatetime(new Date());
        sysUser.setUpdatetime(new Date());
        sysUser.setSex("1");
        sysUser.setTelephone("18556512118");
        userService.save(sysUser);
    }

    @Test
    public void test1(){
        SysUser byId = userService.getById(1);
        System.out.println(byId);
        SysRoleUser sysRoleUser = roleUserMapper.selectById(1);
        System.out.println(sysRoleUser);
    }

    @Test
    public void saveUserByController(){
        UserDto sysUser = new UserDto();
        sysUser.setUsername("fuqiang");
        sysUser.setNickname("fuqiang");
        sysUser.setPassword("000000");
        sysUser.setEmail("fuqiang@qq.com");
        sysUser.setSex("1");
        sysUser.setTelephone("18556512118");
        sysUser.setPhone("18556512118");
        sysUserController.addUser( sysUser,1);
    }


}
