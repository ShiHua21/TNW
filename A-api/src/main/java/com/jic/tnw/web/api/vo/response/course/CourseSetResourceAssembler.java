package com.jic.tnw.web.api.vo.response.course;

import com.jic.tnw.db.mysql.tables.pojos.CourseSet;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CourseSetResourceAssembler extends ResourceAssemblerSupport<CourseSet, CourseSetResource> {


    public CourseSetResourceAssembler(Class<?> controllerClass, Class<CourseSetResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CourseSetResource toResource(CourseSet entity) {
        CourseSetResource courseSetResource = new CourseSetResource();

        return courseSetResource;
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
