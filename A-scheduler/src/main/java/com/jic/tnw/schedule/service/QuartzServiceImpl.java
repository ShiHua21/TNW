package com.jic.tnw.schedule.service;

import com.jic.tnw.schedule.exception.TaskAlreadyRunException;
import com.jic.tnw.schedule.exception.TaskNotFoundException;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lee5hx on 16/10/20.
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

//    public static void main(String agrs[]) throws Exception {
//
//        //afdf19efba0e018e7876f5cab960f8e22fa1d8d5
//        //System.out.println(new QuartzServiceImpl().sha1JobByStr("gaia-scheduler.CheckWithdrawalStatus99Job.CYCLE_SERIOUS_CHECK_DATA_JOB_GROUP"));
//
//        System.out.println(new QuartzServiceImpl().sha1JobByStr("gaia-scheduler.CheckWithdrawalStatus99Job.CYCLE_SERIOUS_CHECK_DATA_JOB_GROUP"));
//        System.out.println(new QuartzServiceImpl().sha1JobByStr("gaia-scheduler.CheatMgWithdrawalJob.DAILY_JOB_GROUP"));
//    }

//    @Override
//    public List<ScheduleJob> getExecutingJobs() throws Exception {
//
//        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
//        List<ScheduleJob> jobList = new ArrayList<>(executingJobs.size());
//        for (JobExecutionContext executingJob : executingJobs) {
//            ScheduleJob job = new ScheduleJob();
//            JobDetail jobDetail = executingJob.getJobDetail();
//            JobKey jobKey = jobDetail.getKey();
//            Trigger trigger = executingJob.getTrigger();
//            job.setHashId(sha1JobByStr(scheduler.getSchedulerName()+"."+jobKey.getName()+"."+jobKey.getGroup()));
//            job.setSchedName(scheduler.getSchedulerName());
//            job.setJobName(jobKey.getName());
//            job.setJobGroup(jobKey.getGroup());
//            job.setDescription(jobDetail.getDescription());
//            job.setDesc("触发器:" + trigger.getKey());
//            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//            job.setJobStatus(triggerState.name());
//            if (trigger instanceof CronTrigger) {
//                CronTrigger cronTrigger = (CronTrigger) trigger;
//                String cronExpression = cronTrigger.getCronExpression();
//                job.setCronExpression(cronExpression);
//
//                job.setNextFireTime(cronTrigger.getNextFireTime());
//                job.setPreviousFireTime(cronTrigger.getPreviousFireTime());
//            }else {
//                job.setCronExpression("now()");
//            }
//            jobList.add(job);
//        }
//        return jobList;
//    }

    @Override
    public void deleteJob(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new TaskNotFoundException(jobName + "." + jobGroup + ",jobKey不存在!");
        }
        System.out.println(scheduler.getJobDetail(jobKey));
        scheduler.deleteJob(jobKey);
    }

//    private String sha1JobByStr(String str) {
//        String rt = "";
//        try {
//            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
//            sha1.update(str.getBytes());
//            rt = toHex(sha1.digest());
//            //System.out.println(rt);
//            rt = rt.substring(rt.length() - 10, rt.length());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rt;
//    }
//
//
//    private String toHex(byte[] digest) {
//        StringBuilder sb = new StringBuilder();
//        int len = digest.length;
//        String out = null;
//        for (int i = 0; i < len; i++) {
////   out = Integer.toHexString(0xFF & digest[i] + 0xABCDEF); //加任意 salt
//            out = Integer.toHexString(0xFF & digest[i]);//原始方法
//            if (out.length() == 1) {
//                sb.append("0");//如果为1位 前面补个0
//            }
//            sb.append(out);
//        }
//        return sb.toString();
//    }


//    @Override
//    public List<ScheduleJob> getAllJobs() throws Exception {
//
//        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
//        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
//
//
//        List<ScheduleJob> jobList = new ArrayList<>();
//        List<? extends Trigger> triggers;
//        JobDetail jobDetail;
//        ScheduleJob job;
//        for (JobKey jobKey : jobKeys) {
//            job = new ScheduleJob();
//
//            //job.setHashId();
//
//            triggers = scheduler.getTriggersOfJob(jobKey);
//            jobDetail = scheduler.getJobDetail(jobKey);
//
//            job.setHashId(sha1JobByStr(scheduler.getSchedulerName()+"."+jobKey.getName()+"."+jobKey.getGroup()));
//            job.setSchedName(scheduler.getSchedulerName());
//            job.setJobName(jobKey.getName());
//            job.setJobGroup(jobKey.getGroup());
//            job.setDescription(jobDetail.getDescription());
//
//
//
//            for (Trigger trigger : triggers) {
//
//                job.setDesc("触发器:" + trigger.getKey());
//
//                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//                job.setJobStatus(triggerState.name());
//                if (trigger instanceof CronTrigger) {
//                    CronTrigger cronTrigger = (CronTrigger) trigger;
//                    String cronExpression = cronTrigger.getCronExpression();
//                    job.setNextFireTime(cronTrigger.getNextFireTime());
//                    job.setPreviousFireTime(cronTrigger.getPreviousFireTime());
//                    job.setCronExpression(cronExpression);
//                }
//                jobList.add(job);
////            }
//            }
//
//        }
//        return jobList;
//    }

    @Override
    public boolean isJobRun(String jobName, String jobGroup) throws Exception {
        boolean rt = false;
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext executingJob : executingJobs) {
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            if (jobName.equals(jobKey.getName()) && jobGroup.equals(jobKey.getGroup())) {
                rt = true;
                return rt;
            }
        }
        return rt;
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new TaskNotFoundException(jobName + "." + jobGroup + ",jobKey不存在!");
        }
        //System.out.println(scheduler.getJobDetail(jobKey));
        scheduler.pauseJob(jobKey);
    }


    @Override
    public void resumeJob(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new TaskNotFoundException(jobName + "." + jobGroup + ",jobKey不存在!");
        }
        scheduler.resumeJob(jobKey);
    }


    @Override
    public void runOneJob(String jobName, String jobGroup, String userName) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new TaskNotFoundException(jobName + "." + jobGroup + ",jobKey不存在!");
        }
        if (isJobRun(jobName, jobGroup)) {
            throw new TaskAlreadyRunException(jobName + "." + jobGroup + ",已经运行!不允许重复运行!");
        }
        JobDataMap map = new JobDataMap();
        map.put("TRIGGED_USER_NAME", userName);

        scheduler.triggerJob(jobKey, map);
    }

}
