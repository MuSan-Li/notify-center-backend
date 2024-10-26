package com.xiao.notify.model.domain.email.response;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 邮箱配置表响应
 *
 * @author xiao
 * @since 2024-10-22
 */
@Data
public class EmailConfigResp implements Serializable {

    //id
    private Long id;
    //渠道方(host)
    private String host;
    //端口
    private String port;
    //用户名
    private String username;
    //密码
    private String password;
    //配置信息json
    private String props;
    //备注
    private String remarks;
    //状态
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
}

