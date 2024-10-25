package com.xiao.notify.common.enums;

import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotifyTypeEnum {
    EMAIL(1, "邮件"),
    SMS(2, "短信"),
    ;
    private final Integer code;
    private final String desc;

    public static NotifyTypeEnum getNotifyTypeByCode(int code) {
        for (NotifyTypeEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    }
}
