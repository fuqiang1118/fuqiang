package com.asiainfo.oss.monitor.service.impl;

import com.asiainfo.oss.monitor.common.JsonData;
import com.asiainfo.oss.monitor.dao.SysuserMapper;
import com.asiainfo.oss.monitor.entity.Sysuser;
import com.asiainfo.oss.monitor.service.ISysuserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SysuserServiceImpl extends ServiceImpl<SysuserMapper, Sysuser> implements ISysuserService {
    @Resource
    private SysuserMapper userMapper;

    public Sysuser findByUserName(String userName){
        Sysuser user = new Sysuser();
        user.setUsername(userName);
        QueryWrapper<Sysuser> wrapper =  new QueryWrapper<>(user);

        return userMapper.selectOne(wrapper);
    }

    public void saveUser(Sysuser user){
        userMapper.insert(user);
    }

    public IPage getAllUsers(Page page){
        return userMapper.selectPage(page, null);
    }

    public Long getUserId(String userName){

        QueryWrapper<Sysuser> wrapper =  new QueryWrapper<>();
        wrapper.eq("USERNAME",userName).select("ID");
        Sysuser sysuser = userMapper.selectOne(wrapper);
        return sysuser.getId();
    }

    public JsonData deleteUserById(long id, String delete_user_role){
        if (delete_user_role.equals("ROLE_ADMIN")){
            return new JsonData(501,"NO");
        }
        userMapper.deleteById(id);
        return new JsonData(200,"OK");

    }
}
