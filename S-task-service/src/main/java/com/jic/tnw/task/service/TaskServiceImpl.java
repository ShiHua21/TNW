package com.jic.tnw.task.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import com.jic.tnw.db.repository.TasksRepository;
import com.jic.tnw.task.dto.TaskResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by lee5hx on 2017/12/15.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private static final Log LOGGER = LogFactory.getLog(TaskServiceImpl.class);

    private final TasksRepository tasksRepository;

    private final ObjectMapper mapper;


    @Autowired
    TaskServiceImpl(TasksRepository tasksRepository,
                    ObjectMapper mapper) {
        this.tasksRepository = tasksRepository;
        this.mapper = mapper;
    }

    @Override
    public Tasks saveTask(Tasks tasks) throws Exception {

        return tasksRepository.add(tasks);
    }

    @Override
    public Tasks updateExecuteTaskResult(Tasks tasks,TasksStatus status,TaskResult result) {
        tasks.setStatus(status);
        tasks.setEndTime(LocalDateTime.now());
        long end  = tasks.getEndTime().atZone(ZoneId.systemDefault()).toEpochSecond();
        long begin  = tasks.getBeginTime().atZone(ZoneId.systemDefault()).toEpochSecond();
        tasks.setElapsed(end - begin);
        String jsonValue = null;
        try {
            jsonValue = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        tasks.setResult(jsonValue);
        return tasksRepository.update(tasks);
    }


    @Override
    public Tasks saveTask(String name, String triggedUserName) {
        Tasks tasks = new Tasks();
        tasks.setName(name);
        tasks.setBeginTime(LocalDateTime.now());
        tasks.setTriggedUserName(triggedUserName);
        tasks.setStatus(TasksStatus.running);
        return tasksRepository.add(tasks);
    }

    @Override
    public Page<Tasks> findWithPageable(Pageable pageable, String name, String triggedUserName, TasksStatus ...tasksStatus) {
        return tasksRepository.find(pageable,name,triggedUserName,tasksStatus);
    }


}
