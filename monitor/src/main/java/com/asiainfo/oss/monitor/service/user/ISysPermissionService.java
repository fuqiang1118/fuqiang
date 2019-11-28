package com.asiainfo.oss.monitor.service.user;

import com.alibaba.fastjson.JSONArray;
import com.asiainfo.oss.monitor.base.result.Results;
import com.asiainfo.oss.monitor.entity.user.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
public interface ISysPermissionService extends IService<SysPermission> {

    JSONArray getAllPermission();

    List<SysPermission> getPermissionByRoleId(Integer id);
}
