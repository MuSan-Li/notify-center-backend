package com.xiao.notify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiao.notify.model.entity.EmailConfig;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 邮箱配置表(EmailConfig)表数据库访问层
 *
 * @author lh
 * @since 2024-10-22 21:29:46
 */
public interface EmailConfigMapper extends BaseMapper<EmailConfig> {

}

