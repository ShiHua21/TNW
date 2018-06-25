package com.jic.tnw.web.api.vo.response.category;

import com.jic.tnw.db.mysql.tables.pojos.Category;
import com.jic.elearning.web.api.controller.v1.CategoryController;
import com.jic.tnw.web.api.controller.v1.CategoryController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author lee5hx
 * @date 2018/03/25
 */
public class CategoryResourceAssembler extends ResourceAssemblerSupport<Category, CategoryResource> {


    public CategoryResourceAssembler(Class<?> controllerClass, Class<CategoryResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public CategoryResource toResource(Category entity) {

        CategoryResource categoryResource = new CategoryResource();
        categoryResource.setCategoryId(entity.getId());
        categoryResource.setCtierCode(entity.getCtierCode());
        categoryResource.setChild(null);
        categoryResource.setCode(entity.getCode());
        categoryResource.setOrgCode(entity.getOrgCode());
        categoryResource.setOrgId(entity.getOrgId());
        categoryResource.setDescription(entity.getDescription());
        categoryResource.setGroupId(entity.getGroupId());
        categoryResource.setName(entity.getName());
        categoryResource.setIcon(entity.getIcon());
        categoryResource.setWeight(entity.getWeight());
        categoryResource.setParentId(entity.getParentId());
        Method method = null;
        try {
            method = CategoryController.class.getMethod("getCategory", Integer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Link link = linkTo(method, entity.getId()).withSelfRel();
        categoryResource.add(link);
        return categoryResource;
    }


    @Override
    public List<CategoryResource> toResources(Iterable<? extends Category> entities) {
        List<CategoryResource> categoryResources = new ArrayList<>();
        List<Category> list = (List<Category>) entities;
        Map<Integer, List<CategoryResource>> map = list.stream()
                .map(category -> toResource(category))
                .collect(groupingBy(
                        t -> t.getParentId().intValue()
                ));
        List<CategoryResource> list1 = map.get(0);
        if (list1 != null) {
            for (CategoryResource cr : list1) {
                to(cr, map);
                categoryResources.add(cr);
            }
        }
        return categoryResources;
    }

    private void to(CategoryResource categoryResource, Map<Integer, List<CategoryResource>> map) {
        List<CategoryResource> list = map.get(categoryResource.getCategoryId());
        if (list != null) {
            for (CategoryResource cr : list) {
                to(cr, map);
            }
            categoryResource.setChild(list);
        }
    }


}
