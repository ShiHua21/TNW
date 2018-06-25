package com.jic.tnw.web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.enums.CourseMemberRole;
import com.jic.tnw.db.mysql.tables.daos.CourseDao;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.db.repository.*;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.common.exception.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author lee5hx
 */
@Service
public class CourseServiceImpl implements CourseService {

    private static final Log LOGGER = LogFactory.getLog(CourseServiceImpl.class);

    private final ObjectMapper mapper;

    private final CourseSetRepository courseSetRepository;

    private final CourseRepository courseRepository;

    private final CourseMemberRepository courseMemberRepository;

    private final CourseChapterRepository courseChapterRepository;

    private final TagRepository tagRepository;

    private final CategoryRepository categoryRepository;

    private final TagOwnerRepository tagOwnerRepository;

    private final ActivityRepository activityRepository;

    private final ActivityTextRepository activityTextRepository;

    private final CourseTaskRepository courseTaskRepository;

    private final UserService userService;

    private final OrgService orgService;

    private final CourseDao courseDao;


    @Autowired
    CourseServiceImpl(ObjectMapper mapper,
                      CourseSetRepository courseSetRepository,
                      CourseRepository courseRepository,
                      CourseMemberRepository courseMemberRepository,
                      CourseChapterRepository courseChapterRepository,
                      TagRepository tagRepository,
                      CategoryRepository categoryRepository,
                      TagOwnerRepository tagOwnerRepository,
                      ActivityRepository activityRepository,
                      ActivityTextRepository activityTextRepository,
                      CourseTaskRepository courseTaskRepository,
                      UserService userService,
                      OrgService orgService, CourseDao courseDao) {
        this.mapper = mapper;
        this.courseSetRepository = courseSetRepository;
        this.courseRepository = courseRepository;
        this.courseMemberRepository = courseMemberRepository;
        this.courseChapterRepository = courseChapterRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.tagOwnerRepository = tagOwnerRepository;
        this.activityRepository = activityRepository;
        this.activityTextRepository = activityTextRepository;
        this.courseTaskRepository = courseTaskRepository;
        this.userService = userService;
        this.orgService = orgService;

        this.courseDao = courseDao;
    }


    @Override
    public Page<CourseSet> findCourseSetsWithPageable(Pageable pageable, Map<String, Object> conditionMap) {
        return courseSetRepository.find(conditionMap, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite addCourse(AddCourseDTO addCourseDTO) {
        CourseSuite courseSuite = new CourseSuite();
        LOGGER.debug(String.format("addCourse = [%s]", addCourseDTO.toString()));
        LocalDateTime nowTime = LocalDateTime.now();
        Org org = orgService.getOrg(addCourseDTO.getOrgId());
        //判断机构是否存在!
        if (org == null) {
            throw new OrgNotExistsException();
        }
        //新增 course_set
        //INSERT INTO course_set_v8 (orgCode, title, type, orgId, status, creator, createdTime, updatedTime)
        //VALUES ('1.', 'RDS-GELOG', 'normal', '1', 'draft', '3', '1523169327', '1523169327')
        CourseSet courseSet = new CourseSet();
        courseSet.setTitle(addCourseDTO.getTitle());
        courseSet.setType(addCourseDTO.getType());
        courseSet.setOrgId(org.getId());
        courseSet.setOrgCode(org.getOrgCode());
        courseSet.setStatus("draft");
        courseSet.setCreatedTime(nowTime);
        courseSet.setUpdatedTime(nowTime);
        courseSet.setCreator(addCourseDTO.getCreateUid());
        courseSet = courseSetRepository.add(courseSet);
        courseSuite.setCourseSet(courseSet);
        LOGGER.debug(String.format("courseSet.id = [%d]", courseSet.getId()));

        //新增 course
        //INSERT INTO course_v8 (serializeMode, type, expiryEndDate, expiryStartDate, expiryDays, maxRate, status, creator, createdTime, updatedTime)
        //VALUES ( 'none', 'normal', '0', '0', '0', '100', 'draft', '3', '1523169327', '1523169327')
        Course course = new Course();
        course.setCourseSetId(courseSet.getId());
        course.setTitle(courseSet.getTitle());
        course.setExpiryMode("forever");
        course.setLearnMode(addCourseDTO.getCourseLearnMode());
        course.setCourseType(courseSet.getType());
        //isDefault = 1
        course.setIsDefault(true);
        //isFree = 1
        course.setIsFree(true);
        course.setSerializeMode("none");
        course.setType(courseSet.getType());
        course.setMaxRate(100);
        course.setStatus(courseSet.getStatus());
        course.setCreator(courseSet.getCreator());
        course.setCreatedTime(nowTime);
        course.setUpdatedTime(nowTime);
        course.setTeacherIds(String.format("|%d|", courseSet.getCreator()));

        course = courseRepository.add(course);
        courseSuite.setCourse(course);
        LOGGER.debug(String.format("course.id = [%d]", courseSet.getId()));
        //新增 course_member
        //INSERT INTO course_member (courseId, courseSetId, userId, role, seq, isVisible, createdTime, updatedTime)
        //VALUES ('27', '27', '3', 'teacher', '0', '1', '1523169327', '1523169327')
        CourseMember courseMember = new CourseMember();
        courseMember.setCourseId(course.getId());
        courseMember.setCourseSetId(courseSet.getId());
        courseMember.setUserId(courseSet.getCreator());
        courseMember.setRole(CourseMemberRole.TEACHER);
        courseMember.setSeq(0);
        courseMember.setIsVisible(true);
        courseMember.setCreatedTime(nowTime);
        courseMember.setUpdatedTime(nowTime);

        courseMember = courseMemberRepository.add(courseMember);
        LOGGER.debug(String.format("courseMember.id = [%d]", courseMember.getId()));

        //todo UPDATE course_set_v8 SET id = '33', type = 'normal', title = '10点13分', subtitle = '', tags = '', categoryId = '0', serializeMode = 'none', status = 'draft', summary = NULL, goals = '', audiences = '', cover = '', ratingNum = '0', rating = '0', noteNum = '0', studentNum = '0', recommended = '0', recommendedSeq = '0', recommendedTime = '0', orgId = '1', orgCode = '1.', discountId = '0', discount = '10.00', hitNum = '0', maxRate = '100', materialNum = '0', parentId = '0', locked = '0', maxCoursePrice = '0.00', minCoursePrice = '0.00', teacherIds = '|3|', creator = '3', createdTime = '1523499241', updatedTime = '1523499241', defaultCourseId = '0' WHERE id = '33'
        //todo UPDATE course_set_v8 SET id = '33', type = 'normal', title = '10点13分', subtitle = '', tags = '', categoryId = '0', serializeMode = 'none', status = 'draft', summary = NULL, goals = '', audiences = '', cover = '', ratingNum = '0', rating = '0', noteNum = '0', studentNum = '0', recommended = '0', recommendedSeq = '0', recommendedTime = '0', orgId = '1', orgCode = '1.', discountId = '0', discount = '10.00', hitNum = '0', maxRate = '100', materialNum = '0', parentId = '0', locked = '0', maxCoursePrice = '0.00', minCoursePrice = '0.00', teacherIds = '|3|', creator = '3', createdTime = '1523499241', updatedTime = '1523499241', defaultCourseId = '0' WHERE id = '33'

        //UPDATE course_set_v8 SET defaultCourseId = '33', updatedTime = '1523499242' WHERE id = '33'
        CourseSet upd = new CourseSet();
        upd.setDefaultCourseId(course.getId());
        upd.setUpdatedTime(LocalDateTime.now());
        upd.setId(courseSet.getId());
        courseSetRepository.update(upd);

        return courseSuite;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite setBaseCource(SetBaseCourseDTO setBaseCourseDTO) {
// UPDATE course_set_v8 SET title = '10点56分', subtitle = '111', tags = '|3|', categoryId = '17', orgCode = '1.4.', serializeMode = 'none', orgId = '4', updatedTime = '1523600556' WHERE id = '34'
        if (!StringUtils.isEmpty(setBaseCourseDTO.getOrgId())) {
            Org org = orgService.getOrg(setBaseCourseDTO.getOrgId());
            //判断机构是否存在!
            if (org == null) {
                throw new OrgNotExistsException();
            }
            setBaseCourseDTO.setOrgCode(org.getOrgCode());
        } else {
            setBaseCourseDTO.setOrgCode("0.");
            setBaseCourseDTO.setOrgId(0);

        }

        if (!StringUtils.isEmpty(setBaseCourseDTO.getCategoryId())) {
            Category category = categoryRepository.findById(setBaseCourseDTO.getCategoryId());
            //判断分类是否存在
            if (category == null) {
                throw new CategoryNotExistsException();
            }
        } else {
            setBaseCourseDTO.setCategoryId(0);
        }

        List<String> codeList = new ArrayList<>();
        for (String tag : setBaseCourseDTO.getTags()) {
            Tag tagById = tagRepository.findById(Integer.valueOf(tag));
            if (tagById == null) {
                throw new TagIdNotFoundException();
            }
            codeList.add(tag);

        }
        String tags = codeList.stream().collect(Collectors.joining(","));

        CourseSuite courseSuite = new CourseSuite();

        CourseSet courseSet = new CourseSet();
        courseSet.setTitle(setBaseCourseDTO.getTitle());
        courseSet.setSubtitle(setBaseCourseDTO.getSubTitle());
        courseSet.setTags(tags);
        courseSet.setCategoryId(setBaseCourseDTO.getCategoryId());
        courseSet.setOrgCode(setBaseCourseDTO.getOrgCode());
        courseSet.setOrgId(setBaseCourseDTO.getOrgId());
        courseSet.setId(setBaseCourseDTO.getCourseId());
        courseSet.setSerializeMode("none");
        courseSet.setUpdatedTime(LocalDateTime.now());
        courseSet = courseSetRepository.update(courseSet);

        courseSuite.setCourseSet(courseSet);
        //UPDATE course_v8 SET categoryId = '17', updatedTime = '1523600556' WHERE courseSetId = '34'
        //UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', updatedTime = '1523600556' WHERE id = '34'
        Course course = new Course();
        course.setCategoryId(setBaseCourseDTO.getCategoryId());
        course.setTitle(setBaseCourseDTO.getTitle());
        course.setSerializeMode("none");
        course.setId(setBaseCourseDTO.getCourseId());
        course.setUpdatedTime(LocalDateTime.now());

        course = courseRepository.update(course);

        courseSuite.setCourse(course);

        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //INSERT INTO tag_owner (ownerType, ownerId, tagId, userId, createdTime) VALUES ('course-set', '34', '3', '3', '1523600556')
        List<TagOwner> tagOwners = tagOwnerRepository.findByOwnIdAndOwnType(setBaseCourseDTO.getCourseId(), "course-set");
        if (tagOwners != null) {
            for (TagOwner tagOwner : tagOwners) {
                tagOwnerRepository.delete(tagOwner.getId());
            }
        }
        TagOwner entry = new TagOwner();
        entry.setOwnerType("course-set");
        entry.setOwnerId(setBaseCourseDTO.getCourseId());
        entry.setUserId(setBaseCourseDTO.getUserId());
        entry.setCreatedTime(LocalDateTime.now());

        for (String tag : codeList) {
            entry.setTagId(Integer.valueOf(tag));
            TagOwner t = tagOwnerRepository.add(entry);
        }

        return courseSuite;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite setDetailCourse(SetDetailCourseDTO setDetailCourseDTO) {
        //UPDATE course_set_v8 SET summary = '<p><strong><span style=\"font-size:22px;\">大啊大奥多大大</span></strong></p>\r\n', goals = '|课程目标|', audiences = '|活人|', updatedTime = '1523601124' WHERE id = '34'
        //UPDATE course_v8 SET categoryId = '17', updatedTime = '1523601124' WHERE courseSetId = '34'
        //UPDATE course_v8 SET title = '10点56分', categoryId = '17', serializeMode = 'none', summary = '<p><strong><span style=\"font-size:22px;\">大啊大奥多大大</span></strong></p>\r\n', goals = '|课程目标|', audiences = '|活人|', updatedTime = '1523601125' WHERE id = '34'
        //DELETE FROM tag_owner WHERE ownerId = '34' AND ownerType = 'course-set'
        //todo 优化写法 用流操作
        CourseSuite courseSuite = new CourseSuite();

        List<String> goalList = new ArrayList<>();
        for (String goal : setDetailCourseDTO.getGoals()) {
            goalList.add(goal);
        }
        String goals = goalList.stream().collect(Collectors.joining(","));
        List<String> audienceList = new ArrayList<>();
        for (String audience : setDetailCourseDTO.getAudiences()) {
            audienceList.add(audience);
        }
        String audiences = audienceList.stream().collect(Collectors.joining(","));
        Course course = new Course();
        course.setUpdatedTime(LocalDateTime.now());
        course.setSummary(setDetailCourseDTO.getSummary());
        course.setGoals(goals);
        course.setAudiences(audiences);
        course.setId(setDetailCourseDTO.getCourseId());
        course = courseRepository.updateByDetail(course);
        courseSuite.setCourse(course);
        CourseSet courseSet = new CourseSet();
        courseSet.setUpdatedTime(LocalDateTime.now());
        courseSet.setSummary(goals);
        courseSet.setSerializeMode("none");
        courseSet.setGoals(goals);
        courseSet.setAudiences(audiences);
        courseSet.setId(setDetailCourseDTO.getCourseId());
        courseSet = courseSetRepository.update(courseSet);
        courseSuite.setCourseSet(courseSet);
        return courseSuite;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseChapter addCourseChapter(AddCourseChapterDTO addCourseChapter) {

        //INSERT INTO course_chapter (title, type, courseId, createdTime) VALUES ('新增一个节', 'unit', '34', '1523864759')
        CourseChapter courseChapter = new CourseChapter();
        courseChapter.setTitle(addCourseChapter.getTitle());
        courseChapter.setCourseId(addCourseChapter.getCourseId());
        courseChapter.setType(addCourseChapter.getType());
        courseChapter.setCreatedTime(LocalDateTime.now());
        courseChapter = courseChapterRepository.add(courseChapter);

        //UPDATE course_chapter SET seq = CASE id  WHEN 3 THEN '1'  WHEN 7 THEN '2'  WHEN 6 THEN '4'  WHEN 4 THEN '7'  WHEN 5 THEN '8'  WHEN 8 THEN '12'  ELSE seq END,number = CASE id  WHEN 3 THEN '1'  WHEN 7 THEN '1'  WHEN 6 THEN '2'  WHEN 4 THEN '3'  WHEN 5 THEN '2'  WHEN 8 THEN '1'  ELSE number END WHERE id IN (3,7,6,4,5,8)
        //UPDATE course_task SET seq = CASE id  WHEN 25 THEN '3'  WHEN 26 THEN '5'  WHEN 28 THEN '6'  WHEN 29 THEN '9'  WHEN 30 THEN '10'  WHEN 31 THEN '11'  ELSE seq END,number = CASE id  WHEN 25 THEN '1'  WHEN 26 THEN '2'  WHEN 28 THEN ''  WHEN 29 THEN ''  WHEN 30 THEN '3'  WHEN 31 THEN '4'  ELSE number END,updatedTime = CASE id  WHEN 25 THEN '1523864759'  WHEN 26 THEN '1523864759'  WHEN 28 THEN '1523864759'  WHEN 29 THEN '1523864759'  WHEN 30 THEN '1523864759'  WHEN 31 THEN '1523864759'  ELSE updatedTime END WHERE id IN (25,26,28,29,30,31)


        return courseChapter;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<CourseTeacher> setCourseTeachers(SetCourseTeachersDTO setCourseTeachers) {
        List<CourseTeacher> rtList = new ArrayList<>();
        List<SetCourseTeacher> teachersList = setCourseTeachers.getTeachers();
        //teachersList(id) to |3|7|9|
        String idsStr = teachersList.stream()
                .map(setCourseTeacher -> setCourseTeacher.getId())
                .collect(Collectors.joining("|", "|", "|"));
        LOGGER.debug(String.format("Set CourseTeacher.IDS = %s", idsStr));

        //SELECT * FROM course_member WHERE courseId = '35' AND role = 'teacher' ORDER BY seq, createdTime DESC
        List<CourseMember> courseTeacherList = courseMemberRepository.findTeachersByCourseId(setCourseTeachers.getCid());
        for (CourseMember del : courseTeacherList) {
            //DELETE FROM course_member WHERE id = '53'
            courseMemberRepository.delete(del.getId());
        }
        SetCourseTeacher setCourseTeacher;
        CourseMember courseMember;
        CourseTeacher courseTeacher;
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < teachersList.size(); i++) {
            courseMember = new CourseMember();
            courseTeacher = new CourseTeacher();
            setCourseTeacher = teachersList.get(i);
            //INSERT INTO course_member (courseId, courseSetId, userId, role, seq, isVisible, createdTime, updatedTime)
            // VALUES ('35', '35', '3', 'teacher', '0', '1', '1524618333', '1524618333')
            courseMember.setCourseId(setCourseTeachers.getCid());
            courseMember.setCourseSetId(setCourseTeachers.getSid());
            courseMember.setUserId(Integer.parseInt(setCourseTeacher.getId()));
            courseMember.setRole(CourseMemberRole.TEACHER);
            courseMember.setSeq(i);
            courseMember.setIsVisible(setCourseTeacher.getVisible());
            courseMember.setCreatedTime(now);
            courseMember.setUpdatedTime(now);
            courseMember = courseMemberRepository.add(courseMember);
            courseTeacher.setId(courseMember.getId());
            courseTeacher.setUserId(courseMember.getUserId());
            courseTeacher.setVisible(courseMember.getIsVisible());
            rtList.add(courseTeacher);
        }
        //UPDATE course_v8 SET teacherIds = '|3|', updatedTime = '1524618333' WHERE id = '35';
        Course course = new Course();
        course.setTeacherIds(idsStr);
        course.setUpdatedTime(now);
        course.setId(setCourseTeachers.getCid());
        courseRepository.update(course);
        //UPDATE course_set_v8 SET id = '35', type = 'normal', title = 'jnjkhkjkjhkhjkhjk', subtitle = NULL, tags = '|2|', categoryId = '0', serializeMode = 'none', status = 'published', summary = NULL, goals = '', audiences = '', cover = '{\"large\":\"public:\\/\\/default\\/2018\\/04-25\\/0840320263e2027983.jpeg\",\"middle\":\"public:\\/\\/default\\/2018\\/04-25\\/08403202cfa0073511.jpeg\",\"small\":\"public:\\/\\/default\\/2018\\/04-25\\/08403202d67c274277.jpeg\"}', ratingNum = '0', rating = '0', noteNum = '0', studentNum = '0', recommended = '0', recommendedSeq = '0', recommendedTime = '0', orgId = '1', orgCode = '1.', discountId = '0', discount = '10.00', hitNum = '0', maxRate = '100', materialNum = '2', parentId = '0', locked = '0', maxCoursePrice = '0.00', minCoursePrice = '0.00', teacherIds = '|3|', creator = '3', createdTime = '1524127709', updatedTime = '1524618333', defaultCourseId = '35' WHERE id = '35'
        CourseSet courseSet = new CourseSet();
        courseSet.setId(setCourseTeachers.getSid());
        courseSet.setTeacherIds(idsStr);
        courseSet.setUpdatedTime(now);
        courseSetRepository.update(courseSet);
        return rtList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite settingCourse(CourseSetDTO courseSetDTO) {
        CourseSuite courseSuite = new CourseSuite();
        // 新增任务
        //UPDATE course_v8 SET enableFinish = '0', buyable = '1', isFree = '1', originPrice = '0.00', tryLookable = '0', price = '0', coinPrice = '0.00', tryLookLength = '0', buyExpiryTime = '0', updatedTime = '1523859790' WHERE id = '34'
        //UPDATE course_set_v8 SET minCoursePrice = '0.00', maxCoursePrice = '0.00', updatedTime = '1523859790' WHERE id = '34'
        Course course = new Course();
        course.setEnableFinish(courseSetDTO.getEnableFinish());
        course.setBuyable(courseSetDTO.getBuyAble());
        course.setIsFree(true);
        course.setOriginPrice(0.00);
        course.setTryLookable(false);
        course.setPrice(0.00);
        course.setCoinPrice(0.00);
        course.setTryLookLength(0);
        course.setBuyExpiryTime(LocalDateTime.MIN);
        course.setUpdatedTime(LocalDateTime.now());
        course.setId(courseSetDTO.getCid());
        course = courseRepository.updateBySet(course);
        courseSuite.setCourse(course);

        CourseSet courseSet = new CourseSet();
        courseSet.setMaxCoursePrice(0.00);
        courseSet.setMinCoursePrice(0.00);
        courseSet.setUpdatedTime(LocalDateTime.now());
        courseSet.setId(courseSetDTO.getCid());
        courseSet = courseSetRepository.update(courseSet);
        courseSuite.setCourseSet(courseSet);
        return courseSuite;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseTask addCourseTask(AddCourseTaskDTO addCourseTask) {

        // 新增任务
        //INSERT INTO activity_text (finishDetail, createdUserId, createdTime, updatedTime)
        //VALUES ('1', '3', '1523859017', '1523859017')
        ActivityText activityText = new ActivityText();
        activityText.setFinishDetail(addCourseTask.getFinishDetail());
        activityText.setCreatedUserId(addCourseTask.getCreateUid());
        activityText.setCreatedTime(LocalDateTime.now());
        activityText.setUpdatedTime(LocalDateTime.now());
        activityText = activityTextRepository.add(activityText);

        //INSERT INTO activity (mediaType, fromCourseId, title, content, fromUserId, fromCourseSetId, mediaId, createdTime)
        //VALUES ('text', '34', '嘿嘿', '<p>嘿嘿</p>\n', '3', '34', '14', '1523859017')
        Activity activity = new Activity();
        activity.setMediaType(addCourseTask.getMediaType());
        activity.setFromCourseId(addCourseTask.getFromCourseId());
        activity.setTitle(addCourseTask.getTitle());
        activity.setContent(addCourseTask.getContent());
        activity.setFromUserId(addCourseTask.getCreateUid());
        activity.setFromCourseSetId(addCourseTask.getFromCourseSetId());
        activity.setMediaId(activityText.getId());
        activity = activityRepository.add(activity);

        //INSERT INTO course_task (title, isOptional, fromCourseSetId, activityId, createdUserId, courseId, type, endTime, createdTime, updatedTime)
        //VALUES ('测试练习题', '1', '34', '29', '3', '34', 'exercise', '0', '1523858033', '1523858033')
        CourseTask courseTask = new CourseTask();
        courseTask.setTitle(addCourseTask.getTitle());
        courseTask.setIsOptional(addCourseTask.getOptional());
        courseTask.setFromCourseSetId(addCourseTask.getFromCourseSetId());
        courseTask.setActivityId(activity.getId());
        courseTask.setCreatedUserId(addCourseTask.getCreateUid());
        courseTask.setCourseId(addCourseTask.getFromCourseId());
        courseTask.setType("text");
        courseTask.setCreatedTime(LocalDateTime.now());
        courseTask.setUpdatedTime(LocalDateTime.now());

        courseTask = courseTaskRepository.add(courseTask);


        //todo UPDATE course_v8 SET taskNum = '2', compulsoryTaskNum = '2', updatedTime = '1523858033' WHERE id = '34'
        Integer taskNum = 0;
        Integer compulsoryTaskNum = 0;
        Course course = new Course();
        course.setId(addCourseTask.getFromCourseId());
        course.setTaskNum(taskNum);
        course.setCompulsoryTaskNum(compulsoryTaskNum);
        courseRepository.update(course);
        return courseTask;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sortCourseTask(SortCourseTaskDTO sortCourseTask) {
        List<String> sortIds = CollectionUtils.arrayToList(sortCourseTask.getSortIds());
        List<CtItem> sortResult = new ArrayList<>();
        String[] sortItem;
        CtItem ctItem;
        Integer chapterNumber = 1;
        Integer unitNumber = 1;
        Integer taskNumber = 1;
        for (int i = 0; i < sortIds.size(); i++) {
            sortItem = sortIds.get(i).split("-", -1);
            switch (sortItem[0]) {
                case "chapter":
                    ctItem = new ChapterItem();
                    ctItem.setId(Integer.valueOf(sortItem[1]));
                    ctItem.setSeq(i + 1);
                    ctItem.setNumber(chapterNumber);
                    chapterNumber++;
                    unitNumber = 1;
                    sortResult.add(ctItem);
                    break;
                case "unit":
                    ctItem = new UnitItem();
                    ctItem.setId(Integer.valueOf(sortItem[1]));
                    ctItem.setSeq(i + 1);
                    ctItem.setNumber(unitNumber);
                    unitNumber++;
                    sortResult.add(ctItem);
                    break;
                case "task":
                    ctItem = new TaskItem();
                    ctItem.setId(Integer.valueOf(sortItem[1]));
                    ctItem.setSeq(i + 1);
                    ctItem.setNumber(taskNumber);
                    taskNumber++;
                    sortResult.add(ctItem);
                    break;
                default:
                    break;
            }
        }
        sortResult.stream().forEach(ctItem1 -> {
            if (ctItem1 instanceof ChapterItem || ctItem1 instanceof UnitItem) {
                CourseChapter courseChapter = new CourseChapter();
                courseChapter.setId(ctItem1.getId());
                courseChapter.setSeq(ctItem1.getSeq());
                courseChapter.setNumber(ctItem1.getNumber());
                courseChapterRepository.update(courseChapter);
            }
            if (ctItem1 instanceof TaskItem) {
                CourseTask courseTask = new CourseTask();
                courseTask.setId(ctItem1.getId());
                courseTask.setSeq(ctItem1.getSeq());
                courseTask.setNumber(String.valueOf(ctItem1.getNumber()));
                courseTaskRepository.update(courseTask);
            }
        });

    }


    @Override
    @Transactional(readOnly = true)
    public List<CtItem> getCourseCtItems(Integer sid, Integer cid) {

        // SELECT * FROM course_task WHERE courseId = '34' ORDER  BY seq
        // SELECT * FROM course_chapter WHERE courseId = '34' ORDER BY createdTime ASC
        List<CtItem> result = new ArrayList<>();
        List<CourseTask> courseTaskList = courseTaskRepository.findByCourseId(sid);
        List<CourseChapter> courseChapterList = courseChapterRepository.findByCourseId(sid);

        CtItem item;
        for (CourseChapter courseChapter : courseChapterList) {
            if ("chapter".equals(courseChapter.getType())) {
                item = new ChapterItem();
                item.setSid(sid);
                item.setCid(cid);
                item.setId(courseChapter.getId());
                item.setSeq(courseChapter.getSeq());
                item.setNumber(Integer.valueOf(courseChapter.getNumber()));
                item.setName(courseChapter.getTitle());
                result.add(item);
            }
            if ("unit".equals(courseChapter.getType())) {
                item = new UnitItem();
                item.setSid(sid);
                item.setCid(cid);
                item.setId(courseChapter.getId());
                item.setSeq(courseChapter.getSeq());
                item.setNumber(Integer.valueOf(courseChapter.getNumber()));
                item.setName(courseChapter.getTitle());
                result.add(item);
            }
        }
        for (CourseTask courseTask : courseTaskList) {
            item = new TaskItem();
            item.setSid(sid);
            item.setCid(cid);
            item.setId(courseTask.getId());
            item.setSeq(courseTask.getSeq());
            item.setNumber(Integer.valueOf(courseTask.getNumber()));
            item.setName(courseTask.getTitle());
            result.add(item);
        }

        result = result.stream()
                .sorted(Comparator.comparing(CtItem::getSeq))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseChapter deleteCourseChapter(Integer chapterId) {
        if (courseChapterRepository.findById(chapterId) == null) {
            throw new CourseChapterNotExistException();
        }
        return courseChapterRepository.delete(chapterId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseChapter updateCourseChapter(Integer chapterId, String title) {
        if (courseChapterRepository.findById(chapterId) == null) {
            throw new CourseChapterNotExistException();
        }
        CourseChapter courseChapter = new CourseChapter();
        courseChapter.setId(chapterId);
        courseChapter.setTitle(title);
        return courseChapterRepository.update(courseChapter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseChapter getCourseChapter(Integer chapterId) {
        if (courseChapterRepository.findById(chapterId) == null) {
            throw new CourseChapterNotExistException();
        }
        return courseChapterRepository.findById(chapterId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite setCoursePublish(Integer courseSetId) {
        //UPDATE course_v8 SET status = 'published', updatedTime = '1524446035' WHERE id = '35'
        //UPDATE course_set_v8 SET minCoursePrice = '0.00', maxCoursePrice = '0.00', updatedTime = '1524446035' WHERE id = '35'
        //UPDATE course_set_v8 SET defaultCourseId = '35', updatedTime = '1524446035' WHERE id = '35'
        //UPDATE course_set_v8 SET status = 'published', updatedTime = '1524446035' WHERE id = '35'
        CourseSuite courseSuite = new CourseSuite();
        CourseSet courseSetBySId = courseSetRepository.findById(courseSetId);
        if (courseSetBySId == null) {
            throw new CourseSetNotExistException();
        }
        CourseSet courseSet = new CourseSet();
        courseSet.setId(courseSetId);
        courseSet.setMinCoursePrice(0.00);
        courseSet.setMaxCoursePrice(0.00);
        courseSet.setUpdatedTime(LocalDateTime.now());
        courseSet.setStatus("published");
        courseSet.setDefaultCourseId(courseSetId);
        courseSet = courseSetRepository.update(courseSet);
        courseSuite.setCourseSet(courseSet);

        Course course = new Course();
        course.setId(courseSetBySId.getDefaultCourseId());
        course.setStatus("published");
        course.setUpdatedTime(LocalDateTime.now());
        course = courseRepository.updateByPublished(course);
        courseSuite.setCourse(course);


        return courseSuite;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CourseSuite getCourseSuiteById(Integer courseId) {
        CourseSuite courseSuite = new CourseSuite();
        CourseSet courseSet = courseSetRepository.findById(courseId);
        if (courseSet == null) {
            throw new CourseSetNotExistException();
        }
        courseSuite.setCourseSet(courseSet);
        Course course = courseRepository.findById(courseSet.getDefaultCourseId());
        if (course == null) {
            throw new CourseSetNotExistException();
        }
        courseSuite.setCourse(course);
        return courseSuite;
    }

}
