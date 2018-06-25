package com.jic.tnw.schedule.task;

import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import com.jic.tnw.task.dto.TaskResult;
import com.jic.tnw.task.service.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lee5hx on 2017/12/25.
 */
public abstract class BaseTask implements Job {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Log LOGGER = LogFactory.getLog(BaseTask.class);

    @Autowired
    private TaskService taskService;

    abstract String executeTask(JobExecutionContext context, Integer taskId,String triggedUserName) throws Exception;

    abstract String getTaskName();

    abstract String getTriggedUserName();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Tasks tasks = null;
        TaskResult tr = new TaskResult();
        String rt;
        String triggedUserName;
        JobDataMap map = context.getTrigger().getJobDataMap();
        if (!map.isEmpty() && !StringUtils.isEmpty(map.getString("TRIGGED_USER_NAME"))) {
            triggedUserName = map.getString("TRIGGED_USER_NAME");
        } else {
            triggedUserName = getTriggedUserName();
        }
        LOGGER.info(String.format("%s-Tasks : %s : Execution Time - {%s}",
                getTaskName(),
                triggedUserName,
                dateFormat.format(new Date())));

        try {
            tasks = taskService.saveTask(getTaskName(), triggedUserName);
            rt = executeTask(context,tasks.getId(), triggedUserName);
            tr.setMessage(rt);
            taskService.updateExecuteTaskResult(tasks, TasksStatus.success, tr);
        } catch (Exception e) {
            LOGGER.error(String.format("%s-执行异常", getTaskName()), e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            tr.setMessage(sw.toString());
            taskService.updateExecuteTaskResult(tasks, TasksStatus.failure, tr);
        }
    }


}
