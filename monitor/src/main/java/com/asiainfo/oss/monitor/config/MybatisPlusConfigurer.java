package com.asiainfo.oss.monitor.config;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @program: hn_monitor
 * @description: MybatisPlus配置类
 * @author: fuqiang
 * @date: 2019-11-12 10:11
 **/
@Configuration
@MapperScan("com.asiainfo.oss.monitor.mapper")
public class MybatisPlusConfigurer {

    /**
     * Sequence主键自增
     *
     * @return 返回oracle自增类
     * @author fuqiang
     * @date 2019/11/12
     */
    @Bean
    public OracleKeyGenerator oracleKeyGenerator(){
        return new OracleKeyGenerator();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
