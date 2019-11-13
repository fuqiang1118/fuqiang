package com.asiainfo.oss.monitor.config;

import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: hn_monitor
 * @description: MybatisPlus������
 * @author: fuqiang
 * @date: 2019-11-12 10:11
 **/
@Configuration
@MapperScan("com.asiainfo.oss.monitor.dao")
public class MybatisPlusConfigurer {

    /**
     * Sequence��������
     *
     * @return ����oracle������
     * @author fuqiang
     * @date 2019/11/12
     */
    @Bean
    public OracleKeyGenerator oracleKeyGenerator(){
        return new OracleKeyGenerator();
    }

    /**
     * ��ҳ���
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
