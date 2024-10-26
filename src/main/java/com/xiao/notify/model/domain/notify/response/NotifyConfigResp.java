package com.xiao.notify.model.domain.notify.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 订阅配置表响应
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
public class NotifyConfigResp implements Serializable {

    //id
    private Long id;
    //订阅名称
    private String name;
    //内容
    private String content;
    //状态
    private Integer status;
    //通知方式 邮件，短信...
    private Integer notifyType;
    //corn表达式
    private String corn;
    //备注
    private String remarks;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
}
