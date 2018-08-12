package com.jic.tnw.thrid.service;

/**
 * Created by lee5hx on 2017/11/27.
 */
public interface ScheduleHttpService {

    void runOneTask(String group,String job,String userName) throws Exception;
}