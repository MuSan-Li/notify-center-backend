package com.xiao.notify.job;

import com.xiao.notify.mapper.NotifyConfigMapper;
import com.xiao.notify.model.entity.NotifyConfig;
import com.xiao.notify.service.NotifyConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    // private final NotifyConfigService notifyConfigService;
    private final NotifyConfigMapper notifyConfigMapper;

    @Bean
    public Scheduler scheduler() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 从数据库读取任务配置，并动态添加到 Quartz 调度器
        List<NotifyConfig> tasks = notifyConfigMapper.getNotifyConfigActiveList();
        for (NotifyConfig task : tasks) {
            QuartzUtils.addTaskToScheduler(scheduler, task);
        }
        return scheduler;
    }
}
