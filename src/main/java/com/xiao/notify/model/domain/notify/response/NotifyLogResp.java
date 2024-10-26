package com.xiao.notify.model.domain.notify.response;

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
public class NotifyLogResp implements Serializable {

    //id
    private Long id;
    //订阅配置id
    private Long notifyConfigId;
    //订阅配置名和曾
    private String notifyConfigName;
    //发送状态
    private Integer status;
    //通知方式 邮件，短信...
    private Integer notifyType;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
}

