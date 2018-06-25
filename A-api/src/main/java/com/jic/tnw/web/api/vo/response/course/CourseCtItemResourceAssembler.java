package com.jic.tnw.web.api.vo.response.course;

import com.jic.elearning.web.api.controller.v1.CourseController;
import com.jic.elearning.web.api.dto.course.ChapterItem;
import com.jic.elearning.web.api.dto.course.CtItem;
import com.jic.elearning.web.api.dto.course.TaskItem;
import com.jic.elearning.web.api.dto.course.UnitItem;
import com.jic.tnw.web.api.controller.v1.CourseController;
import com.jic.tnw.web.api.dto.course.ChapterItem;
import com.jic.tnw.web.api.dto.course.CtItem;
import com.jic.tnw.web.api.dto.course.TaskItem;
import com.jic.tnw.web.api.dto.course.UnitItem;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CourseCtItemResourceAssembler extends ResourceAssemblerSupport<CtItem, CourseCtItemResource> {


    public CourseCtItemResourceAssembler(Class<?> controllerClass, Class<CourseCtItemResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CourseCtItemResource toResource(CtItem entity) {
        CourseCtItemResource courseCtItemResource = new CourseCtItemResource();
        courseCtItemResource.setSid(entity.getSid());
        courseCtItemResource.setCid(entity.getCid());
        courseCtItemResource.setItemId(entity.getId());
        courseCtItemResource.setItemName(entity.getName());
        courseCtItemResource.setItemNumber(entity.getNumber());
        courseCtItemResource.setItemSeq(entity.getSeq());
        if (entity instanceof ChapterItem) {
            courseCtItemResource.setItemType("chapter");
            try {
                courseCtItemResource.add(ControllerLinkBuilder.linkTo(
                        methodOn(CourseController.class)
                                .deleteCourseChapter(
                                        courseCtItemResource.getSid(),
                                        courseCtItemResource.getCid(),
                                        courseCtItemResource.getItemType(),
                                        String.valueOf(courseCtItemResource.getItemId()),
                                        null
                                )
                ).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (entity instanceof UnitItem) {
            courseCtItemResource.setItemType("unit");
            try {
                courseCtItemResource.add(linkTo(
                        methodOn(CourseController.class)
                                .deleteCourseChapter(
                                        courseCtItemResource.getSid(),
                                        courseCtItemResource.getCid(),
                                        courseCtItemResource.getItemType(),
                                        String.valueOf(courseCtItemResource.getItemId()),
                                        null
                                )
                ).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (entity instanceof TaskItem) {
            courseCtItemResource.setItemType("task");
            try {
                courseCtItemResource.add(linkTo(
                        methodOn(CourseController.class)
                                .deleteCourseTasks(
                                        courseCtItemResource.getSid(),
                                        courseCtItemResource.getCid(),
                                        String.valueOf(courseCtItemResource.getItemId()),
                                        null
                                )
                ).withSelfRel());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        courseCtItemResource.setItemIdType(
                String.format("%s-%d",
                        courseCtItemResource.getItemType(),
                        courseCtItemResource.getItemId()
                )
        );

        return courseCtItemResource;
    }
}
