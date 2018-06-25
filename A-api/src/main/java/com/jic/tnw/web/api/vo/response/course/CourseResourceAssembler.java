package com.jic.tnw.web.api.vo.response.course;

import com.jic.tnw.db.mysql.tables.pojos.Course;
import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import com.jic.elearning.web.api.controller.v1.CourseController;
import com.jic.elearning.web.api.dto.course.CourseSuite;
import com.jic.tnw.web.api.dto.course.CourseSuite;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CourseResourceAssembler extends ResourceAssemblerSupport<CourseSuite, CourseResource> {


    public CourseResourceAssembler(Class<?> controllerClass, Class<CourseResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CourseResource toResource(CourseSuite entity) {
        Course course = entity.getCourse();
        CourseSet courseSet = entity.getCourseSet();
        CourseResource courseResource = new CourseResource();
        courseResource.setCourseSetId(courseSet.getId());
        courseResource.setCourseId(course.getId());
        courseResource.setCategoryId(course.getCategoryId());
        courseResource.setTitle(course.getTitle());
        courseResource.setSubTitle(courseSet.getSubtitle());
        courseResource.setStatus(courseSet.getStatus());
        courseResource.setTags(courseSet.getTags());
        courseResource.setOrgId(courseSet.getOrgId());
        courseResource.setOrgCode(courseSet.getOrgCode());
        courseResource.setSummary(courseSet.getSummary());
        courseResource.setGoals(courseSet.getGoals());
        courseResource.setAudiences(courseSet.getAudiences());
        courseResource.setBuyAble(course.getBuyable());
        courseResource.setEnableFinish(course.getEnableFinish());
        return courseResource;
    }
}
