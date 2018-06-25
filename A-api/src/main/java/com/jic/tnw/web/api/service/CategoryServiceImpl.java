package com.jic.tnw.web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.common.exception.CategoryCodeAlreadyOccupiedException;
import com.jic.tnw.common.exception.CategoryCodeExistsException;
import com.jic.tnw.common.exception.CategoryNotExistsException;
import com.jic.tnw.db.mysql.tables.pojos.Category;
import com.jic.tnw.db.mysql.tables.pojos.CategoryGroup;
import com.jic.tnw.db.repository.CategoryGroupRepository;
import com.jic.tnw.db.repository.CategoryRepository;
import com.jic.tnw.user.service.UserService;
import com.jic.elearning.web.api.dto.category.AddCategoryDTO;
import com.jic.elearning.web.api.dto.category.UpdateCategoryDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lee5hx on 2017/10/19.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Log LOGGER = LogFactory.getLog(CategoryServiceImpl.class);

    private final ObjectMapper mapper;

    private final CategoryGroupRepository categoryGroupRepository;

    private final CategoryRepository categoryRepository;

    private final UserService userService;


    @Autowired
    CategoryServiceImpl(ObjectMapper mapper,
                        CategoryGroupRepository categoryGroupRepository,
                        CategoryRepository categoryRepository, UserService userService) {
        this.mapper = mapper;
        this.categoryGroupRepository = categoryGroupRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Override
    public Category deleteById(Integer Id) {
        Category deleted = categoryRepository.findById(Id);
        if (deleted == null) {
            throw new CategoryNotExistsException();
        }
        categoryRepository.deleteLikeTierCode(deleted.getCtierCode());
        return deleted;
    }

    @Override
    public List<CategoryGroup> findCategoryGroups() {
        return categoryGroupRepository.findAll();
    }

    @Override
    public List<Category> findCategoryListByGroupId(Integer groupId) {
        return categoryRepository.findListByGroupId(groupId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Category addCategory(AddCategoryDTO addCategoryDTO) {
        LOGGER.info(String.format("Category Add %s", addCategoryDTO.toString()));
        Integer parentId = addCategoryDTO.getParentId();
        Category parentCategory = categoryRepository.findById(parentId);

        if (parentCategory == null) {
            parentId = 0;
        }
        Category category = categoryRepository.findByCode(addCategoryDTO.getCode());
        if (category != null) {
            throw new CategoryCodeExistsException();
        }
        category = new Category();
        category.setParentId(parentId);
        category.setCode(addCategoryDTO.getCode());
        category.setCtierCode(String.format(""));
        category.setDescription(addCategoryDTO.getDescription());
        category.setGroupId(addCategoryDTO.getGroupId());
        category.setName(addCategoryDTO.getName());
        category.setOrgCode("1.");
        category.setOrgId(1);
        category.setWeight(1);
        category.setIcon("");
        category.setPath("");
        category = categoryRepository.add(category);

        //更新层级树编码
        String ctierCode;
        if (parentCategory == null) {
            ctierCode = String.format("%d.%d.", category.getParentId(), category.getId());
        } else {
            ctierCode = String.format("%s%s.", parentCategory.getCtierCode(), category.getId());
        }
        categoryRepository.updateCtierCodeById(category.getId(), ctierCode);
        return category;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Category updateCategory(UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(updateCategoryDTO.getId());
        if (category == null) {
            throw new CategoryNotExistsException();
        }
        Category codeCategory = categoryRepository.findByCode(updateCategoryDTO.getCode());
        if ((!updateCategoryDTO.getCode().equals(category.getCode())) && codeCategory != null) {
            //编码已被占用,请换一个
            throw new CategoryCodeAlreadyOccupiedException();
        }
        category.setCode(updateCategoryDTO.getCode());
        category.setName(updateCategoryDTO.getName());
        category.setDescription(updateCategoryDTO.getDescription());

        return categoryRepository.update(category);
    }

    @Override
    public Category findById(Integer Id) {
        Category category = categoryRepository.findById(Id);
        if (category == null) {
            throw new CategoryNotExistsException();
        }
        return categoryRepository.findById(Id);
    }


    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Category> getCategoryMap() {
        List<Category> list = categoryRepository.findAll();
        Map<Integer, Category> categoryMap = list.parallelStream()
                .collect(HashMap::new, (map, p) -> map.put(p.getId(), p), Map::putAll);
        return categoryMap;
    }
}
