package com.xiao.notify.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 邮箱配置表(EmailConfig)表实体类
 *
 * @author xiao
 * @since 2024-10-22 21:29:46
 */
@Data
@TableName(value = "t_email_config")
public class EmailConfig implements Serializable {

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //渠道方(host)
    @TableField("host")
    private String host;
    //端口
    @TableField("port")
    private String port;
    //用户名
    @TableField("username")
    private String username;
    //密码
    @TableField("password")
    private String password;
    //配置信息json
    @TableField("props")
    private String props;
    //备注
    @TableField("remarks")
    private String remarks;
    //状态
    @TableField("status")
    private Integer status;
    //创建时间
    @TableField("create_time")
    private LocalDateTime createTime;
    //更新时间
    @TableField("update_time")
    private LocalDateTime updateTime;
    //是否删除
    @TableLogic
    @TableField("is_delete")
    private Integer isDelete;
}

