package com.jic.tnw.web.api.service;

import com.jic.tnw.db.mysql.tables.pojos.Category;
import com.jic.tnw.db.mysql.tables.pojos.CategoryGroup;
import com.jic.elearning.web.api.dto.category.AddCategoryDTO;
import com.jic.elearning.web.api.dto.category.UpdateCategoryDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by lee5hx on 2018/3/6.
 */
public interface CategoryService {
    List<CategoryGroup> findCategoryGroups();
    Category addCategory(AddCategoryDTO addCategoryDTO);
    Category updateCategory(UpdateCategoryDTO updateCategoryDTO);
    List<Category> findCategoryListByGroupId(Integer groupId);
    Category findById(Integer Id);
    Category deleteById(Integer Id);
    Map<Integer, Category> getCategoryMap();
}
