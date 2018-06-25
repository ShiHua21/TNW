package com.jic.tnw.schedule.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SendGoodsTask extends BaseTask {

    private static final Log LOGGER = LogFactory.getLog(SendGoodsTask.class);


    private static final String TRIGGED_USER_NAME = "scheduled";
    private static final String TASK_NAME = "SEND_GOODS";

//    @Autowired
//    private SendGoodsService sendGoodsService;

    @Override
    String executeTask(JobExecutionContext context, Integer taskId, String triggedUserName) throws Exception {
        return "ok";
    }

    @Override
    String getTaskName() {
        return TASK_NAME;
    }

    @Override
    String getTriggedUserName() {
        return TRIGGED_USER_NAME;
    }
}