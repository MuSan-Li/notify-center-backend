package com.xiao.notify.job;

import com.xiao.notify.service.NotifyConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 短信通知任务
 *
 * @author xiao
 * @date 2024-12-08
 */
@Slf4j
@Component
public class SmsNotifyJob implements Job {
    @Resource
    private NotifyConfigService notifyConfigService;

    @Override
    public void execute(JobExecutionContext context) {
        // 任务逻辑
        log.info("执行动态任务: context:{}", context.getJobDetail().getKey());
        // NotifySendRequest request = new NotifySendRequest();
        // notifyConfigService.sendNotify(request, (SafetyUser) null);
    }
}
