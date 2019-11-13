package com.asiainfo.oss.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Accessors(chain = true)
@TableName("ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键roleId
     */
    @TableId("ID")
    private BigDecimal id;

    /**
     * 角色名称
     */
    @TableField("NAME")
    private String name;


}
