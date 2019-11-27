package com.asiainfo.oss.monitor.entity.user;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("SYS_PERMISSION")
@KeySequence(value = "seq_permissionid", clazz = Long.class)
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.INPUT)
    private Long id;

    @TableField("PARENTID")
    private Long parentid;

    @TableField("NAME")
    private String name;

    @TableField("CSS")
    private String css;

    @TableField("HREF")
    private String href;

    @TableField("TYPE")
    private String type;

    @TableField("PERMISSION")
    private String permission;

    @TableField("SORT")
    private Long sort;


}
