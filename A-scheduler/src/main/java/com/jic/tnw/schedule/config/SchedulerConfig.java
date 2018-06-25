package com.jic.tnw.schedule.config;

import com.jic.tnw.common.constant.TaskConstant;
import com.jic.tnw.schedule.spring.AutowiringSpringBeanJobFactory;
import com.jic.tnw.schedule.task.CheckServerStatusTask;
import com.jic.tnw.schedule.task.SendGoodsTask;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * https://github.com/davidkiss/spring-boot-quartz-demo
 */
@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {





    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext
        // injecting SpringLiquibase to ensure liquibase is already initialized and created the quartz tables:
        //  SpringLiquibase springLiquibase
    ) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory,
                                          @Qualifier("checkServerStatusTaskTrigger") Trigger checkServerStatusTaskTrigger,
                                          @Qualifier("sendGoodsTaskTrigger") Trigger sendGoodsTaskTrigger
    ) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setSchedulerName("OC2-Scheduler");
        factory.setAutoStartup(true);


        factory.setQuartzProperties(quartzProperties());
        factory.afterPropertiesSet();
        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);


        //检测服务器状态任务
        scheduler.deleteJob(JobKey.jobKey("checkServerStatusTaskDetail", TaskConstant.JOB_GROUP_NAME));
        scheduler.scheduleJob((JobDetail) checkServerStatusTaskTrigger.getJobDataMap().get("jobDetail"), checkServerStatusTaskTrigger);
        //自动发货任务
        scheduler.deleteJob(JobKey.jobKey("sendGoodsTaskDetail", TaskConstant.JOB_GROUP_NAME));
        scheduler.scheduleJob((JobDetail) sendGoodsTaskTrigger.getJobDataMap().get("jobDetail"), sendGoodsTaskTrigger);



        scheduler.start();
        return scheduler;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    // ----- 检测服务器状态任务 -----
    @Bean
    public JobDetailFactoryBean checkServerStatusTaskDetail() {
        return createJobDetail(CheckServerStatusTask.class);
    }
    @Bean(name = "checkServerStatusTaskTrigger")
    public CronTriggerFactoryBean checkServerStatusTaskTrigger(@Qualifier("checkServerStatusTaskDetail") JobDetail jobDetail) {
        return createCronTrigger(jobDetail, "0/30 * * * * ?");
    }

    // ----- 自动发货任务 -----
    @Bean
    public JobDetailFactoryBean sendGoodsTaskDetail() {
        return createJobDetail(SendGoodsTask.class);
    }
    @Bean(name = "sendGoodsTaskTrigger")
    public CronTriggerFactoryBean sendGoodsTaskTrigger(@Qualifier("sendGoodsTaskDetail") JobDetail jobDetail) {
        return createCronTrigger(jobDetail, "0 0/30 * * * ?");
    }




    private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setGroup(TaskConstant.JOB_GROUP_NAME);
        // task has to be durable to be stored in DB:
        factoryBean.setDurability(true);
        return factoryBean;
    }

    private static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // in case of misfire, ignore all missed triggers and continue :
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    // Use this method for creating cron triggers instead of simple triggers:
    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setGroup(TaskConstant.TRIGGER_GROUP_NAME);
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

}