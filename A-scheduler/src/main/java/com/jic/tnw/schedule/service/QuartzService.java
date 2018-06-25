package com.jic.tnw.schedule.service;


import org.quartz.SchedulerException;

/**
 * Quartz 操作接口
 * @author lee5hx
 */

public interface QuartzService {

//    /**
//     * 获取正在执行JOB
//     * @return
//     * @throws SchedulerException
//     */
//    List<ScheduleJob> getExecutingJobs() throws Exception;
//
//
//    /**
//     * 获取所有JOB
//     * @return
//     * @throws SchedulerException
//     */
//    List<ScheduleJob> getAllJobs() throws Exception;

    /**
     * 停止JOB
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    void pauseJob(String jobName, String jobGroup) throws Exception;


    /**
     * 删除JOB
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    void deleteJob(String jobName, String jobGroup) throws Exception;


    /**
     * 恢复JOB 运行
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    void resumeJob(String jobName, String jobGroup) throws Exception;

    /**
     * 运行一次JOB
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    void runOneJob(String jobName, String jobGroup,String userName) throws Exception;

    /**
     * 查看JOB是否在运行中
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    boolean isJobRun(String jobName, String jobGroup) throws Exception;
}
