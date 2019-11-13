package com.asiainfo.oss.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Integer id;

    @TableField("USERID")
    private Integer userid;

    @TableField("ROLEID")
    private Integer roleid;

    public UserRole(Integer userID,Integer roleID){
        this.userid = userID;
        this.roleid = roleID;
    }

}
