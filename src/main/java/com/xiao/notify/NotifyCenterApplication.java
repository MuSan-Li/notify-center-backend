package com.xiao.notify;


import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author lh
 */
@SpringBootApplication
public class NotifyCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifyCenterApplication.class, args);
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            if (!scheduler.isStarted()) {
                scheduler.start();  // 启动调度器
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}