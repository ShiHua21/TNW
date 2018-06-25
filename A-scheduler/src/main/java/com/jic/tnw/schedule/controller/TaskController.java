package com.jic.tnw.schedule.controller;

import com.jic.tnw.schedule.service.QuartzService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private static final Log LOGGER = LogFactory.getLog(TaskController.class);

    @Autowired
    private QuartzService quartzService;


    @RequestMapping(value = "/task/run-one/{group}/{job}", method = RequestMethod.POST)
    public ResponseEntity<?> runOneJob(@PathVariable String group,
                                       @PathVariable String job,
                                       @RequestBody TaskRunOneRequest taskRunOneRequest) throws Exception {
        LOGGER.info(String.format("runOneJob:%s,%s",group,job));
        quartzService.runOneJob(job,group,taskRunOneRequest.getUserName());
        return ResponseEntity.ok("ok");
    }


}