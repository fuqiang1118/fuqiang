package com.asiainfo.oss.monitor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by fuqiang on 2019/11/11.
 *
 * 视图配置类
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login_page").setViewName("login");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/register_page").setViewName("register");
        registry.addViewController("/display_user_info").setViewName("displayuserinfo");
        registry.addViewController("/change_user_role").setViewName("change_user_role");


    }

}
