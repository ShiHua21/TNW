package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import com.jic.tnw.task.service.TaskService;
import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by lee5hx on 2017/12/10.
 */
@ApiIgnore
@RestController
@RequestMapping("/v1")
@Api(description = "任务信息处理", tags = {"X-任务模块"})
public class TaskController {

    private static final Log LOGGER = LogFactory.getLog(TaskController.class);

    private final LocaleMessageSourceService localeMessageSourceService;

    private final ObjectMapper mapper;

    private final TaskService taskService;


    @Autowired
    public TaskController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,
            TaskService taskService
    ) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.taskService = taskService;
    }


    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "任务列表", notes = "返回任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "任务名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "triggedUserName", value = "触发者", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tasksStatus", value = "状态", dataType = "String", paramType = "query"),
            // @ApiImplicitParam(name = "name", value = "查询条件-角色名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query")
    })
    public ResponseEntity<?> findAll(String name, String triggedUserName, TasksStatus[] tasksStatus, Pageable pageable, PagedResourcesAssembler assembler) throws AuthenticationException {
        Page<Tasks> page = taskService.findWithPageable(pageable, name, triggedUserName, tasksStatus);
        // Return the token
        return ResponseEntity.ok(assembler.toResource(page));
    }

}
