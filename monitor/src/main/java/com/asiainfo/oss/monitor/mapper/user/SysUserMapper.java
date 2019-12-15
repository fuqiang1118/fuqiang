package com.asiainfo.oss.monitor.mapper.user;

import com.asiainfo.oss.monitor.entity.user.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Update("update sys_user t set t.password = #{password} where t.id = #{id}")
    int changePassword(@Param("id") Long id, @Param("password") String password);
}
