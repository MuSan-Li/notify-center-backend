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
 * 订阅配置表(NotifySendRequest)表实体类
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
@TableName(value = "t_notify_config")
public class NotifyConfig implements Serializable {

    //id
    @TableId(type = IdType.AUTO)
    private Long id;
    //订阅名称
    @TableField("name")
    private String name;
    //内容
    @TableField("content")
    private String content;
    //状态
    @TableField("status")
    private Integer status;
    //通知方式 邮件，短信...
    @TableField("notify_type")
    private Integer notifyType;

    //corn表达式
    @TableField("corn")
    private String corn;
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
