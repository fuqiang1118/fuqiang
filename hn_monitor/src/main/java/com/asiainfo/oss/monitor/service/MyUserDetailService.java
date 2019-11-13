package com.asiainfo.oss.monitor.service;

import com.asiainfo.oss.monitor.entity.Sysuser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuqiang on 2019/11/11.
 *
 *
 */

@Component("MyUserDetailService")
@Slf4j
public class MyUserDetailService implements UserDetailsService{



    @Autowired
    private ISysuserService userService;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "admin";
            }
        });
//        log.info("{}","传进来的名字"+s);
//        log.info("{}","验证。。。。。");
        Sysuser user = userService.findByUserName(s);//数据库查询操作
        if (user == null){
            throw new UsernameNotFoundException("");
        }
        return user;

    }
}
