package com.jic.tnw.web.api.vo.response.category;

import com.jic.tnw.db.mysql.tables.pojos.CategoryGroup;
import com.jic.elearning.web.api.controller.v1.CategoryController;
import com.jic.tnw.web.api.controller.v1.CategoryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.lang.reflect.Method;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CategoryGroupResourceAssembler extends ResourceAssemblerSupport<CategoryGroup, CategoryGroupResource> {


    public CategoryGroupResourceAssembler(Class<?> controllerClass, Class<CategoryGroupResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CategoryGroupResource toResource(CategoryGroup entity) {
        CategoryGroupResource categoryGroupResource = new CategoryGroupResource();
        categoryGroupResource.setCategoryGroupId(entity.getId());
        categoryGroupResource.setCode(entity.getCode());
        categoryGroupResource.setDepth(entity.getDepth());
        categoryGroupResource.setName(entity.getName());
        Method method = null;
        try {
            method = CategoryController.class.getMethod("getCategories", Integer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Link link = linkTo(method, entity.getId()).withSelfRel();
        categoryGroupResource.add(link);
        return categoryGroupResource;
    }
}
