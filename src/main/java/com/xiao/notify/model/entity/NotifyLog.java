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
 * 订阅信息记录表(NotifyLog)表实体类
 *
 * @author lh
 * @since 2024-10-22 21:29:47
 */
@Data
@TableName(value = "t_notify_log")
public class NotifyLog implements Serializable {

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //订阅配置id
    @TableField("notify_config_id")
    private Long notifyConfigId;
    //订阅配置名称
    @TableField("notify_config_name")
    private String notifyConfigName;
    //发送状态
    @TableField("status")
    private Integer status;
    //通知方式 邮件，短信...
    @TableField("notify_type")
    private Integer notifyType;
    //备注
    @TableField("remarks")
    private String remarks;
    @TableField("user_id")
    private Long userId;
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

