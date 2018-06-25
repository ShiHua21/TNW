package com.jic.tnw.web.api.vo.response.course;

import com.jic.tnw.db.mysql.tables.pojos.CourseChapter;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CourseChapterResourceAssembler extends ResourceAssemblerSupport<CourseChapter, CourseChapterResource> {


    public CourseChapterResourceAssembler(Class<?> controllerClass, Class<CourseChapterResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CourseChapterResource toResource(CourseChapter entity) {
        CourseChapterResource courseChapterResource = new CourseChapterResource();
        courseChapterResource.setCourseChapterId(entity.getId());
        courseChapterResource.setCourseId(entity.getCourseId());
        courseChapterResource.setTitle(entity.getTitle());
        courseChapterResource.setType(entity.getType());
        courseChapterResource.setCreatedTime(entity.getCreatedTime());
        return courseChapterResource;
    }

    //    @Override
//    public CategoryGroupResource toResource(CategoryGroup entity) {
//        CategoryGroupResource categoryGroupResource = new CategoryGroupResource();
//        categoryGroupResource.setCategoryGroupId(entity.getId());
//        categoryGroupResource.setCode(entity.getCode());
//        categoryGroupResource.setDepth(entity.getDepth());
//        categoryGroupResource.setName(entity.getName());
//        Method method = null;
//        try {
//            method = CategoryController.class.getMethod("getCategories", Integer.class);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        Link link = linkTo(method, entity.getId()).withSelfRel();
//        categoryGroupResource.add(link);
//        return categoryGroupResource;
//    }
}
