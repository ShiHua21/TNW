package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.enums.CourseLearnMode;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.service.CategoryService;
import com.jic.elearning.web.api.service.CourseService;
import com.jic.elearning.web.api.vo.response.HomeResource;
import com.jic.tnw.web.api.vo.request.*;
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
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author lee5hx
 * @date 2018/3/21
 */
@RestController
@RequestMapping("/v1")
@Api(description = "课程管理", tags = {"G-课程模块-5"})
public class CourseController {

    private static final Log LOGGER = LogFactory.getLog(CourseController.class);
    private final LocaleMessageSourceService localeMessageSourceService;
    private final ObjectMapper mapper;
    private final CourseService courseService;
    private final OrgService orgService;
    private final UserService userService;
    private final CategoryService categoryService;

    private CourseResourceAssembler courseResourceAssembler = new CourseResourceAssembler(CourseController.class, CourseResource.class);
    private CourseChapterResourceAssembler courseChapterResourceAssembler = new CourseChapterResourceAssembler(CourseController.class, CourseChapterResource.class);
    private CourseCtItemResourceAssembler courseCtItemResourceAssembler = new CourseCtItemResourceAssembler(CourseController.class, CourseCtItemResource.class);


    @Autowired
    public CourseController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper,
            CourseService courseService, OrgService orgService, UserService userService, CategoryService categoryService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.courseService = courseService;
        this.orgService = orgService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    @ApiOperation(value = "课程列表", notes = "课程列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")})
    public ResponseEntity<?> getCourses(
            @AuthenticationPrincipal UserDetails user) throws Exception {
        Integer userId = Integer.parseInt(user.getUsername());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @RequestMapping(value = "/course_sets", method = RequestMethod.GET)
    @ApiOperation(value = "课程管理列表", notes = "课程管理列表(非超级管理员权限只能查看)", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "org", value = "机构", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "category", value = "分类", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "creator", value = "创建者", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getCourseSets(
            String org,
            String category,
            String status,
            String title,
            String creator,
            Pageable pageable,
            PagedResourcesAssembler assembler,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {

        Map<String, Object> conditionMap = new HashMap<>(10);

        if (!StringUtils.isEmpty(org)) {
            conditionMap.put("org", org);
        }
        if (!StringUtils.isEmpty(category)) {
            conditionMap.put("category", category);
        }
        if (!StringUtils.isEmpty(status)) {
            conditionMap.put("status", status);
        }
        if (!StringUtils.isEmpty(title)) {
            conditionMap.put("title", title);
        }

        SimpleGrantedAuthority sgaSA = new SimpleGrantedAuthority("ROLE_SUPER_ADMIN");
        if (principalUser.getAuthorities().contains(sgaSA)) {
            if (!StringUtils.isEmpty(creator)) {
                User user = userService.findByName(creator);
                if (user == null) {
                    conditionMap.put("creator", 0);
                } else {
                    conditionMap.put("creator", user.getId());
                }
            }
        } else {
            conditionMap.put("creator", Integer.parseInt(principalUser.getUsername()));
        }

        Map<Integer, Org> orgMap = orgService.getOrgMap();
        Map<Integer, Category> categoryMap = categoryService.getCategoryMap();
        Page<CourseSet> page = courseService.findCourseSetsWithPageable(pageable, conditionMap);
        Page<CourseResource> newPage = page.map(source -> {
            CourseResource courseResource = new CourseResource();
            courseResource.setCourseId(source.getDefaultCourseId());
            courseResource.setCourseSetId(source.getId());
            courseResource.setTitle(source.getTitle());
            Category category1 = categoryMap.get(source.getCategoryId().intValue());
            if (category1 == null) {
                courseResource.setCategoryId(0);
                courseResource.setCategoryName("");
            } else {
                courseResource.setCategoryId(category1.getId());
                courseResource.setCategoryName(category1.getName());
            }
            courseResource.setCover(source.getCover());
            Org org1 = orgMap.get(source.getOrgId().intValue());
            courseResource.setOrgId(org1.getId());
            courseResource.setOrgCode(org1.getOrgCode());

            courseResource.setOrgName(org1.getName());
            courseResource.setSerializeMode(source.getSerializeMode());
            courseResource.setStudentNum(source.getStudentNum());
            courseResource.setStatus(source.getStatus());
            courseResource.setCreator(source.getCreator());
            JelUser jelUser = userService.findById(source.getCreator());
            User user = jelUser.getUser();
            courseResource.setCreatorName(user.getUsername());
            try {
                courseResource.add(linkTo(methodOn(CourseController.class).setCourseBase(courseResource.getCourseSetId(), null, null)).withRel("patch_set_course_base"));
                courseResource.add(linkTo(methodOn(CourseController.class).setCourseDetail(courseResource.getCourseSetId(), null)).withRel("patch_set_course_detail"));
                courseResource.add(linkTo(methodOn(CourseController.class).setCourseCover(courseResource.getCourseSetId())).withRel("patch_set_course_cover"));
                courseResource.add(linkTo(methodOn(CourseController.class).setCourseMarketing(courseResource.getCourseSetId(), courseResource.getCourseId(), null,null)).withRel("patch_set_course_setting"));
                courseResource.add(linkTo(methodOn(CourseController.class).addCourseChapter(courseResource.getCourseSetId(), courseResource.getCourseId(), "chapter", null, null)).withRel("post_add_course_task_chapter"));
                courseResource.add(linkTo(methodOn(CourseController.class).addCourseChapter(courseResource.getCourseSetId(), courseResource.getCourseId(), "unit", null, null)).withRel("post_add_course_task_unit"));
                courseResource.add(linkTo(methodOn(CourseController.class).addCourseTasks(courseResource.getCourseSetId(), courseResource.getCourseId(), null, null)).withRel("post_add_course_task"));
                courseResource.add(linkTo(methodOn(CourseController.class).sortCourseItems(courseResource.getCourseSetId(), courseResource.getCourseId(), null, null)).withRel("post_sort_course_items"));
                courseResource.add(linkTo(methodOn(CourseController.class).getCourse(courseResource.getCourseSetId())).withRel("get_course_info"));
                courseResource.add(linkTo(methodOn(CourseController.class).setCourseTeachers(courseResource.getCourseSetId(), courseResource.getCourseId(), null, null)).withRel("patch_set_course_teachers"));
                courseResource.add(linkTo(methodOn(CourseController.class).matchCourseTeachers(courseResource.getCourseSetId(), courseResource.getCourseId(), null, null)).withRel("get_match_course_teachers"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return courseResource;
        });

        PagedResources ok = assembler.toResource(newPage);

        return new ResponseEntity<>(ok, HttpStatus.OK);
    }


    @RequestMapping(value = "/course", method = RequestMethod.POST)
    @ApiOperation(value = "新增课程", notes = "新增课程", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addCourse", value = "新增分类", required = true, dataType = "AddCourseVO")
    })
    public ResponseEntity<?> addCourse(
            @Validated @RequestBody AddCourseVO addCourse,
            @AuthenticationPrincipal UserDetails user) throws Exception {
        Integer userId = Integer.parseInt(user.getUsername());
        AddCourseDTO addCourseDTO = new AddCourseDTO();
        addCourseDTO.setCourseLearnMode(CourseLearnMode.valueOf(addCourse.getCourseLearnMode()));
        addCourseDTO.setCreateUid(userId);
        addCourseDTO.setTitle(addCourse.getTitle());
        addCourseDTO.setOrgId(addCourse.getOrgId());
        addCourseDTO.setType("normal");
        CourseSuite courseSuite = courseService.addCourse(addCourseDTO);
        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID获取课程", notes = "根据ID获取课程", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "课程ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> getCourse(@PathVariable Integer id) throws Exception {
        CourseSuite courseSuite = null;
        courseSuite = courseService.getCourseSuiteById(id);
        Map<Integer, Org> orgMap = orgService.getOrgMap();
        Map<Integer, Category> categoryMap = categoryService.getCategoryMap();
        CourseResource courseResourceForChange = courseResourceAssembler.toResource(courseSuite);
        Category category = categoryMap.get(courseSuite.getCourse().getCategoryId().intValue());
        if (category == null) {
            courseResourceForChange.setCategoryId(0);
            courseResourceForChange.setCategoryName("");
        } else {
            courseResourceForChange.setCategoryId(category.getId());
            courseResourceForChange.setCategoryName(category.getName());
        }
        Org org1 = orgMap.get(courseSuite.getCourseSet().getOrgId().intValue());
        courseResourceForChange.setOrgId(org1.getId());
        courseResourceForChange.setOrgCode(org1.getName());

        return new ResponseEntity<>(courseResourceForChange, HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{id}/manage/base", method = RequestMethod.PATCH)
    @ApiOperation(value = "设置课程基本信息", notes = "设置课程基本信息", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "setBaseCourseVO", value = "课程基本信息", required = true, dataType = "SetBaseCourseVO"),

    })
    public ResponseEntity<?> setCourseBase(
            @PathVariable Integer id, @Validated @RequestBody SetBaseCourseVO setBaseCourseVO, @AuthenticationPrincipal UserDetails user) throws Exception {
        CourseSuite courseSuite = null;
        SetBaseCourseDTO setBaseCourseDTO = new SetBaseCourseDTO();
        setBaseCourseDTO.setCourseId(id);
        setBaseCourseDTO.setCategoryId(setBaseCourseVO.getCategoryId());
        setBaseCourseDTO.setOrgId(setBaseCourseVO.getOrgId());
        setBaseCourseDTO.setStatus(setBaseCourseVO.getStatus());
        setBaseCourseDTO.setTitle(setBaseCourseVO.getTitle());
        setBaseCourseDTO.setSubTitle(setBaseCourseVO.getSubTitle());
        setBaseCourseDTO.setTags(setBaseCourseVO.getTags());
        setBaseCourseDTO.setUserId(Integer.parseInt(user.getUsername()));
        //UPDATE course_set_v8 SET title = '10点56分', subtitle = '111', tags = '|3|', categoryId = '17', orgCode = '1.4.', serializeMode = 'none', orgId = '4', updatedTime = '1523600556' WHERE id = '34'
        //UPDATE course_v8 SET categoryId = '17', updatedTime = '1523600556' WHERE courseSetId = '34'
        //UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', updatedTime = '1523600556' WHERE id = '34'
        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //INSERT INTO tag_owner (ownerType, ownerId, tagId, userId, createdTime) VALUES ('course-set', '34', '3', '3', '1523600556')

        courseSuite = courseService.setBaseCource(setBaseCourseDTO);

        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{id}/manage/detail", method = RequestMethod.PATCH)
    @ApiOperation(value = "设置课程基详细信息", notes = "设置课程基详细信息", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "setDetailCourseVO", value = "课程详细信息", required = true, dataType = "SetDetailCourseVO"),

    })
    public ResponseEntity<?> setCourseDetail(
            @PathVariable Integer id, @RequestBody SetDetailCourseVO setDetailCourseVO) throws Exception {
        CourseSuite courseSuite = null;
        SetDetailCourseDTO setDetailCourseDTO = new SetDetailCourseDTO();
        setDetailCourseDTO.setAudiences(setDetailCourseVO.getAudiences());
        setDetailCourseDTO.setGoals(setDetailCourseVO.getGoals());
        setDetailCourseDTO.setSummary(setDetailCourseVO.getSummary());
        setDetailCourseDTO.setCourseId(id);
        courseSuite = courseService.setDetailCourse(setDetailCourseDTO);

        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);

        //UPDATE course_set_v8 SET summary = '<p><strong><span style=\"font-size:22px;\">大啊大奥多大大</span></strong></p>\r\n', goals = '|课程目标|', audiences = '|活人|', updatedTime = '1523601124' WHERE id = '34'
        //UPDATE course_v8 SET categoryId = '17', updatedTime = '1523601124' WHERE courseSetId = '34'
        //UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', summary = '<p><strong><span style=\"font-size:22px;\">大啊大奥多大大</span></strong></p>\r\n', goals = '|课程目标|', audiences = '|活人|', updatedTime = '1523601125' WHERE id = '34'
        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //INSERT INTO tag_owner (ownerType, ownerId, tagId, userId, createdTime) VALUES ('course-set', '34', '3', '3', '1523601125')

    }


    @RequestMapping(value = "/course_set/{id}/manage/cover", method = RequestMethod.PATCH)
    @ApiOperation(value = "设置课程封面图片", notes = "设置课程封面图片", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "课程ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> setCourseCover(
            @PathVariable Integer id) throws Exception {
        CourseSuite courseSuite = null;


        //UPDATE course_set_v8 SET cover = '{\"large\":\"public:\\/\\/default\\/2018\\/04-13\\/143750ee087a143542.jpeg\",\"middle\":\"public:\\/\\/default\\/2018\\/04-13\\/143750ee107c034827.jpeg\",\"small\":\"public:\\/\\/default\\/2018\\/04-13\\/143750ee1623796631.jpeg\"}', updatedTime = '1523601470' WHERE id = '34'

        //UPDATE course_v8 SET categoryId = '17', updatedTime = '1523601470' WHERE courseSetId = '34'
        //UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', summary = '<p><strong><span style=\"font-size:22px;\">大啊大奥多大大</span></strong></p>\r\n', goals = '|课程目标|', audiences = '|活人|', updatedTime = '1523601471' WHERE id = '34'

        //DELETE FROM file WHERE uri = 'public://tmp/2018/04-13/143745a01238176892.jpeg'
        //INSERT INTO file (userId, groupId, mime, size, uri, createdTime) VALUES ('3', '51', '', '65675', 'public://tmp/2018/04-13/143745a01238176892.jpeg', '1523601465')
        //INSERT INTO file (userId, groupId, mime, size, uri, createdTime) VALUES ('3', '45', '', '18521', 'public://default/2018/04-13/143750ee087a143542.jpeg', '1523601470')
        //INSERT INTO file (userId, groupId, mime, size, uri, createdTime) VALUES ('3', '45', '', '8995', 'public://default/2018/04-13/143750ee107c034827.jpeg', '1523601470')
        //INSERT INTO file (userId, groupId, mime, size, uri, createdTime) VALUES ('3', '45', '', '1661', 'public://default/2018/04-13/143750ee1623796631.jpeg', '1523601470')

        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //INSERT INTO tag_owner (ownerType, ownerId, tagId, userId, createdTime) VALUES ('course-set', '34', '3', '3', '1523601471')


        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/tasks", method = RequestMethod.GET)
    @ApiOperation(value = "课程任务列表", notes = "课程任务列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> getCourseTasks(
            @PathVariable Integer sid,
            @PathVariable Integer cid) throws Exception {
        // 任务列表
        List<CtItem> list = courseService.getCourseCtItems(sid, cid);
        return new ResponseEntity<>(courseCtItemResourceAssembler.toResources(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/task", method = RequestMethod.POST)
    @ApiOperation(value = "对课程进行新增任务", notes = "对课程进行新增任务", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "addCourseTask", value = "任务信息", required = true, dataType = "AddCourseTaskVO")
    })
    public ResponseEntity<?> addCourseTasks(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @RequestBody AddCourseTaskVO addCourseTask,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        //CourseSuite courseSuite = null;
        // 新增任务
        //INSERT INTO course_draft (activityId, title, content, courseId, userId, createdTime) VALUES ('0', '', '<p>嘿嘿</p>\n', '34', '3', '1523859016')
        //INSERT INTO activity_text (finishDetail, createdUserId, createdTime, updatedTime) VALUES ('1', '3', '1523859017', '1523859017')
        //INSERT INTO activity (mediaType, fromCourseId, title, content, fromUserId, fromCourseSetId, mediaId, createdTime) VALUES ('text', '34', '嘿嘿', '<p>嘿嘿</p>\n', '3', '34', '14', '1523859017')
        //DELETE FROM course_draft WHERE courseId = '34' AND activityId = '0' AND userId = '3'
        //INSERT INTO course_task (title, isOptional, fromCourseSetId, activityId, createdUserId, courseId, type, endTime, createdTime, updatedTime) VALUES ('测试练习题', '1', '34', '29', '3', '34', 'exercise', '0', '1523858033', '1523858033')
        //UPDATE course_v8 SET taskNum = '2', compulsoryTaskNum = '2', updatedTime = '1523858033' WHERE id = '34'
        //UPDATE course_chapter SET seq = CASE id  WHEN 3 THEN '1'  WHEN 6 THEN '2'  WHEN 4 THEN '6'  WHEN 5 THEN '7'  ELSE seq END,number = CASE id  WHEN 3 THEN '1'  WHEN 6 THEN '1'  WHEN 4 THEN '2'  WHEN 5 THEN '2'  ELSE number END WHERE id IN (3,6,4,5)
        //UPDATE course_task SET seq = CASE id  WHEN 26 THEN '3'  WHEN 25 THEN '4'  WHEN 28 THEN '5'  WHEN 29 THEN '8'  ELSE seq END,number = CASE id  WHEN 26 THEN '1'  WHEN 25 THEN '2'  WHEN 28 THEN ''  WHEN 29 THEN ''  ELSE number END,updatedTime = CASE id  WHEN 26 THEN '1523858033'  WHEN 25 THEN '1523858033'  WHEN 28 THEN '1523858033'  WHEN 29 THEN '1523858033'  ELSE updatedTime END WHERE id IN (26,25,28,29)

        Integer userId = Integer.parseInt(principalUser.getUsername());
        AddCourseTaskDTO addCourseTaskDTO = new AddCourseTaskDTO();
        addCourseTaskDTO.setFromCourseSetId(sid);
        addCourseTaskDTO.setFromCourseId(cid);
        addCourseTaskDTO.setFinishDetail(addCourseTask.getFinishDetail());
        addCourseTaskDTO.setMediaType(addCourseTask.getMediaType());
        addCourseTaskDTO.setCreateUid(userId);
        addCourseTaskDTO.setTitle(addCourseTask.getTitle());
        addCourseTaskDTO.setContent(addCourseTask.getContent());
        addCourseTaskDTO.setOptional(addCourseTask.getOptional());
        return new ResponseEntity<>(courseService.addCourseTask(addCourseTaskDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/chapter/{type}", method = RequestMethod.POST)
    @ApiOperation(value = "对课程进行新增章or节", notes = "对课程进行新增章or节", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "章节类型(chapter-章，unit-节)", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "addCourseChapter", value = "新增章节", required = true, dataType = "AddCourseChapterVO")
    })
    public ResponseEntity<?> addCourseChapter(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @PathVariable String type,
            @Validated @RequestBody AddCourseChapterVO addCourseChapter,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        //type chapter-章节，unit-单元，

        LOGGER.info(String.format("addCourseChapter sid = %d,cid = %d,type= %s", sid, cid, type));
        AddCourseChapterDTO addCourseChapterDTO = new AddCourseChapterDTO();
        addCourseChapterDTO.setCourseId(cid);
        addCourseChapterDTO.setType(type);
        addCourseChapterDTO.setTitle(addCourseChapter.getTitle());
        CourseChapter courseChapter = courseService.addCourseChapter(addCourseChapterDTO);
        return new ResponseEntity<>(courseChapterResourceAssembler.toResource(courseChapter), HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/chapter/{type}/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除课程章or节", notes = "删除课程章or节", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "章节类型(chapter-章，unit-节)", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "chapter-ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> deleteCourseChapter(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @PathVariable String type,
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        //type chapter-章节，unit-单元，
        //删除节
        //DELETE FROM course_chapter WHERE id = '30'
        //DELETE FROM course_chapter WHERE copyId = '30'
        //INSERT INTO log (module, action, message, data, userId, ip, createdTime, level) VALUES ('course', 'delete_chapter', '删除章节(#30)', '{\"id\":\"30\",\"courseId\":\"34\",\"type\":\"unit\",\"number\":\"4\",\"seq\":\"30\",\"title\":\"asdf\",\"createdTime\":\"1524190819\",\"copyId\":\"0\"}', '3', '123.234.103.95', '1524191127', 'info')
        //删除章
        //DELETE FROM course_chapter WHERE id = '25'
        // DELETE FROM course_chapter WHERE copyId = '25'
        //INSERT INTO log (module, action, message, data, userId, ip, createdTime, level) VALUES ('course', 'delete_chapter', '删除章节(#25)', '{\"id\":\"25\",\"courseId\":\"34\",\"type\":\"chapter\",\"number\":\"4\",\"seq\":\"26\",\"title\":\"qwer\",\"createdTime\":\"1524190410\",\"copyId\":\"0\"}', '3', '123.234.103.95', '1524191407', 'info')
        CourseChapter courseChapter = courseService.deleteCourseChapter(Integer.valueOf(id));
        return new ResponseEntity<>(courseChapterResourceAssembler.toResource(courseChapter), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/chapter/{type}/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取课程章or节", notes = "获取课程章or节", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "章节类型(chapter-章，unit-节)", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "chapter-ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> getCourseChapter(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @PathVariable String type,
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        //type chapter-章节，unit-单元，
        CourseChapter courseChapter = courseService.getCourseChapter(Integer.valueOf(id));
        return new ResponseEntity<>(courseChapterResourceAssembler.toResource(courseChapter), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/chapter/{type}/{id}", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改课程章or节", notes = "修改课程章or节", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "章节类型(chapter-章，unit-节)", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "chapter-ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editCourseChapterVO", value = "修改课程章节标题", required = true, dataType = "EditCourseChapterVO"),

    })
    public ResponseEntity<?> editCourseChapter(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @PathVariable String type,
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails principalUser, @Validated @RequestBody EditCourseChapterVO editCourseChapterVO) throws Exception {
        //type chapter-章节，unit-单元，
        CourseChapter courseChapter = courseService.updateCourseChapter(Integer.valueOf(id), editCourseChapterVO.getTitle());
        return new ResponseEntity<>(courseChapterResourceAssembler.toResource(courseChapter), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/task/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除课程任务", notes = "删除课程任务", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "id", value = "task-ID", required = true, paramType = "path", dataType = "int"),

    })
    public ResponseEntity<?> deleteCourseTasks(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {

        return new ResponseEntity<>("ok", HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/items/sort", method = RequestMethod.POST)
    @ApiOperation(value = "对课程,章节,任务进行排序", notes = "对课程,章节,任务进行排序", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "sortItems", value = "排序ids", required = true, dataType = "SortItemsVO")
    })
    public ResponseEntity<?> sortCourseItems(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @Validated @RequestBody SortItemsVO sortItems,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {

        SortCourseTaskDTO sortCourseTask = new SortCourseTaskDTO();
        sortCourseTask.setCid(cid);
        sortCourseTask.setSid(sid);
        sortCourseTask.setSortIds(sortItems.getIds());
        courseService.sortCourseTask(sortCourseTask);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/marketing", method = RequestMethod.PATCH)
    @ApiOperation(value = "课程-学习设置", notes = "课程-学习设置", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "courseSetVO", value = "学习设置", required = true, dataType = "CourseSetVO"),
    })
    public ResponseEntity<?> setCourseMarketing(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @Validated @RequestBody CourseSetVO courseSetVO,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {

        CourseSuite courseSuite = null;
        // 新增任务
        //CourseSuite courseSuite = null;
        // 学习设置
        //UPDATE course_v8 SET enableFinish = '0', buyable = '1', isFree = '1', originPrice = '0.00', tryLookable = '0', price = '0', coinPrice = '0.00', tryLookLength = '0', buyExpiryTime = '0', updatedTime = '1523859790' WHERE id = '34'
        //UPDATE course_set_v8 SET minCoursePrice = '0.00', maxCoursePrice = '0.00', updatedTime = '1523859790' WHERE id = '34'
        CourseSetDTO courseSetDTO = new CourseSetDTO();
        courseSetDTO.setCid(cid);
        courseSetDTO.setSid(sid);
        courseSetDTO.setEnableFinish(courseSetVO.getEnableFinish());
        courseSetDTO.setBuyAble(courseSetVO.getBuyAble());
        courseSuite = courseService.settingCourse(courseSetDTO);
        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/teachers", method = RequestMethod.PATCH)
    @ApiOperation(value = "课程-讲师设置", notes = "课程-讲师设置", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "courseSetTeachers", value = "讲师设置", required = true, dataType = "CourseSetTeachersVO"),
    })
    public ResponseEntity<?> setCourseTeachers(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            @Validated @RequestBody CourseSetTeachersVO courseSetTeachers,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        SetCourseTeachersDTO setCourseTeachers = new SetCourseTeachersDTO();
        setCourseTeachers.setSid(sid);
        setCourseTeachers.setCid(cid);
        setCourseTeachers.setTeachers(courseSetTeachers.getTeachers());
        List<CourseTeacher> list = courseService.setCourseTeachers(setCourseTeachers);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/course/{cid}/teachers_match", method = RequestMethod.GET)
    @ApiOperation(value = "课程-匹配讲师", notes = "课程-匹配讲师", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "query", value = "匹配查询条件", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> matchCourseTeachers(
            @PathVariable Integer sid,
            @PathVariable Integer cid,
            String query,
            @AuthenticationPrincipal UserDetails principalUser) throws Exception {
        List<User> list = userService.findTeachersByUserName(query);
        List<MatchCourseTeacher> rt = list.stream().map(user -> {
            MatchCourseTeacher matchCourseTeacher = new MatchCourseTeacher();
            matchCourseTeacher.setId(user.getId());
            matchCourseTeacher.setAvatar("");
            matchCourseTeacher.setUserName(user.getUsername());
            return matchCourseTeacher;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(rt, HttpStatus.OK);
    }


    @RequestMapping(value = "/course_set/{sid}/manage/publish", method = RequestMethod.PATCH)
    @ApiOperation(value = "课程管理-发布课程", notes = "课程管理-发布课程-发布课程", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            //@ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> setCoursePublish(
            @PathVariable Integer sid
            //@PathVariable Integer cid
    ) throws Exception {

        CourseSuite courseSuite = null;
        //UPDATE course_v8 SET status = 'published', updatedTime = '1524446035' WHERE id = '35'
        //UPDATE course_set_v8 SET status = 'published', updatedTime = '1524446035' WHERE id = '35'

        courseSuite = courseService.setCoursePublish(sid);
        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);
    }

    @RequestMapping(value = "/course_subject/{sid}/manage/{cid}/add/subject", method = RequestMethod.POST)
    @ApiOperation(value = "题目管理-新增答题", notes = "题目管理-新增答题", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "sid", value = "SET-课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "课程ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "addCourseSubjectVO", value = "新增答题", required = true, paramType = "path", dataType = "AddCourseSubjectVO"),

    })
    public ResponseEntity<?> addCourseSubject(
            @PathVariable Integer sid,
            @PathVariable Integer cid,@Validated @RequestBody AddCourseSubjectVO addCourseSubjectVO
    ) throws Exception {

        CourseSuite courseSuite = null;
        //单选提  INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', '0', 'normal', '<p>sad</p>\r\n', '[\"0\"]', '', '2', 'single_choice', '0', '33', '3', '3', '1524452510', '1524452510', '{\"choices\":[\"<p>\\u770b\\u770b<\\/p>\\r\\n\",\"<p>t\\u5077\\u5077<\\/p>\\r\\n\",\"<p>q\\u8bf7\\u6c42<\\/p>\\r\\n\"]}', 'course-33')
        //材料题  INSERT INTO question (courseId, lessonId, difficulty, stem, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'normal', '<p>阿斯顿打算打点滴</p>\r\n', '', '2', 'material', '0', '35', '3', '3', '1524452690', '1524452690', 'course-35')
        //不定向选择题 INSERT INTO question (courseId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', 'normal', '<p>1+1=？</p>\r\n', '[\"1\"]', '', '2', 'uncertain_choice', '0', '35', '3', '3', '1524453904', '1524453904', '{\"choices\":[\"<p>1<\\/p>\\r\\n\",\"<p>2<\\/p>\\r\\n\",\"<p>3<\\/p>\\r\\n\",\"<p>4<\\/p>\\r\\n\"]}', 'course-35')
        //问答题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'difficulty', '<p>1*1=？</p>\r\n', '[\"<p>2<\\/p>\\n\"]', '', '2', 'essay', '0', '35', '3', '3', '1524462252', '1524462252', 'course-35')
        //判断题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'normal', '<p>7*8=56</p>\r\n', '[\"1\"]', '', '2', 'determine', '0', '35', '3', '3', '1524463220', '1524463220', 'course-35')
        //填空题 INSERT INTO question (courseId, lessonId, difficulty, stem, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, answer, target) VALUES ('0', '0', 'normal', '<p>1*3=[[]][[]]23</p>\r\n', '<p>啊当初的纯纯粹粹</p>\n', '2', 'fill', '0', '35', '3', '3', '1524463901', '1524463901', '[[\"]][[\"]]', 'course-35')
        //多选题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', '0', 'normal', '<p>动物类()</p>\r\n', '[\"0\",\"1\"]', '', '2', 'choice', '0', '35', '3', '3', '1524465846', '1524465846', '{\"choices\":[\"<p>\\u732a<\\/p>\\n\",\"<p>\\u72d7<\\/p>\\n\",\"<p>\\u9999\\u8549<\\/p>\\n\",\"<p>\\u9ec4\\u74dc<\\/p>\\n\"]}', 'course-35')

        courseSuite = courseService.setCoursePublish(cid);
        return new ResponseEntity<>(courseResourceAssembler.toResource(courseSuite), HttpStatus.OK);
    }
}
