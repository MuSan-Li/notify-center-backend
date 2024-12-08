package com.xiao.notify.job;

import cn.hutool.core.text.StrPool;
import com.xiao.notify.common.ErrorCode;
import com.xiao.notify.exception.BusinessException;
import com.xiao.notify.model.entity.NotifyConfig;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import java.text.MessageFormat;

@Slf4j
public class QuartzUtils {


    protected static void addTaskToScheduler(Scheduler scheduler, NotifyConfig task) throws Exception {
        // 创建 JobDetail
        Class clazz = Class.forName(task.getJobClassName());
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(getKeyName(task), task.getJobGroup())
                .build();
        // 创建 CronTrigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(getTriggerName(task), task.getJobGroup())
                // 设置 Cron 表达式
                .withSchedule(CronScheduleBuilder.cronSchedule(task.getCorn()))
                .build();
        // 将 Job 和 Trigger 添加到调度器
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private static String getKeyName(NotifyConfig task) {
        return task.getId() + StrPool.DASHED + task.getJobName();
    }

    public static String getTriggerName(NotifyConfig task) {
        return MessageFormat.format("{0}-Trigger", task.getId());
    }

    public static void updateJob(Scheduler scheduler, NotifyConfig task) {
        try {
            TriggerKey triggerKey = new TriggerKey(getTriggerName(task), task.getJobGroup());
            CronTrigger newTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    // 更新 Cron 表达式
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCorn()))
                    .build();
            // 更新调度器中的 Trigger
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (SchedulerException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
