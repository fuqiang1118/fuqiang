package com.asiainfo.oss.monitor.mapper.user;

import com.asiainfo.oss.monitor.entity.user.SysRoleUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    @Select("select userid,roleid from sys_role_user where userid = #{userId}")
    public SysRoleUser getByUserId(Integer userId);
}
