package com.jic.tnw.task.service;

import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import com.jic.tnw.task.dto.TaskResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lee5hx on 2017/12/15.
 */
public interface TaskService {

    Tasks saveTask(Tasks tasks) throws Exception;

    Tasks saveTask(String name, String triggedUserName);

    Tasks updateExecuteTaskResult(Tasks tasks,TasksStatus status,TaskResult result) ;

    Page<Tasks> findWithPageable(Pageable pageable, String name, String triggedUserName, TasksStatus ...tasksStatus);


}
