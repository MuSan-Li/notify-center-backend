package com.xiao.notify.model.domain.notify.request;

import lombok.Data;

import java.io.Serializable;


/**
 * 订阅请求
 *
 * @author lh
 * @since 2024-10-22
 */
@Data
public class NotifyConfigRequest implements Serializable {

    //id
    private Long id;
    //订阅名称
    private String notifyName;
    //内容
    private String content;
    //状态
    private String notifyStatus;
    //通知方式 邮件，短信...
    private Integer notifyType;
    //corn表达式
    private String corn;
    //备注
    private String remarks;
}
