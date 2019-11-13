package com.asiainfo.oss.monitor.service.impl;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.entity.UserRole;
import com.asiainfo.oss.monitor.dao.UserRoleMapper;
import com.asiainfo.oss.monitor.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    public boolean  save(UserRole userAndRole){
        return userRoleMapper.insert(userAndRole)==0?false:true;
    }

    public void setUserAdmin(Integer userID){
        UserRole userRole = new UserRole();
        userRole.setRoleid(1);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("USERID",userID);
        userRoleMapper.update(userRole,wrapper);
    }

    public JsonData setUser(Integer userID){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        List<String> the_roles = new ArrayList<String>();
//        for (GrantedAuthority authority : authorities) {
//            the_roles.add(authority.getAuthority());
//        }
//        if (the_roles.contains("ROLE_ADMIN")){
//            userAndRoleMapper.setUser(userID);
//            return new JsonData(200,"OK");
//        } else {
//            return new JsonData(500,"NO");
//        }
        UserRole userRole = new UserRole();
        userRole.setRoleid(2);
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("USERID",userID);
        userRoleMapper.update(userRole,wrapper);
        return new JsonData(200,"ok");


    }
}
