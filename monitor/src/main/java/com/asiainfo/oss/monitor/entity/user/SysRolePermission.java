package com.asiainfo.oss.monitor.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("SYS_ROLE_PERMISSION")
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ROLEID")
    private Long roleId;

    @TableField("PERMISSIONID")
    private Long permissionId;


}
