package com.asiainfo.oss.monitor.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author fuqiang
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
@KeySequence(value = "seq_userid", clazz = Long.class)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField("NICKNAME")
    private String nickname;

    @TableField("HEADIMGURL")
    private String headimgurl;

    @TableField("PHONE")
    private String phone;

    @TableField("TELEPHONE")
    private String telephone;

    @TableField("EMAIL")
    private String email;

    @TableField("BIRTHDAY")
    private LocalDateTime birthday;

    @TableField("SEX")
    private String sex;

    @TableField("STATUS")
    private String status;

    @TableField("CREATETIME")
    private LocalDateTime createtime;

    @TableField("UPDATETIME")
    private LocalDateTime updatetime;


}
