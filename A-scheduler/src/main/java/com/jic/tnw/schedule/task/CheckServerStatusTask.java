package com.jic.tnw.schedule.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 检测服务状态-任务
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CheckServerStatusTask implements Job {

    private static final Log LOGGER = LogFactory.getLog(CheckServerStatusTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final String CHECK_SERVER_STATUS_REDIS_ID = "check_server_status_msg";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //@Scheduled(cron = "0/30 * * * * ?")
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info(String.format("check-server-status :: Execution Time - {%s}", dateFormat.format(new Date())));
        boolean isAvailable = true;
        if(isAvailable){
            stringRedisTemplate.opsForValue().set(CHECK_SERVER_STATUS_REDIS_ID,"OK");
        }
    }

}