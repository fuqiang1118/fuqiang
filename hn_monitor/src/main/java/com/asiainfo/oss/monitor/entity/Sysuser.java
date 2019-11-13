package com.asiainfo.oss.monitor.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("SYSUSER")
@KeySequence(value = "SEQ_BASE_USER_INFO", clazz = Integer.class)
public class Sysuser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    /**
     * 用户名
     */
    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private String role;

    public Sysuser(String username,String password){
        this.username = username;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<Role> roles = getRoles();
        for(Role role : roles)
        {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
