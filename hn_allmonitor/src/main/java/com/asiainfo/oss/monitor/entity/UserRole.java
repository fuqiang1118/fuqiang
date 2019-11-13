package com.asiainfo.oss.monitor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("USER_ROLE")
@KeySequence(value = "SEQ_BASE_USERROLE_INFO",clazz = Long.class)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("USERID")
    private Long userid;

    @TableField("ROLEID")
    private Long roleid;

    public UserRole(Long userID,Long roleID){
        this.userid = userID;
        this.roleid = roleID;
    }

}
