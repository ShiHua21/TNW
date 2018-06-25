package com.jic.tnw.web.api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Category;
import com.jic.tnw.db.mysql.tables.pojos.CategoryGroup;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.dto.category.AddCategoryDTO;
import com.jic.elearning.web.api.dto.category.UpdateCategoryDTO;
import com.jic.elearning.web.api.service.CategoryService;
import com.jic.elearning.web.api.vo.request.category.AddCategory;
import com.jic.elearning.web.api.vo.request.category.EditCategory;
import com.jic.elearning.web.api.vo.response.category.CategoryGroupResource;
import com.jic.elearning.web.api.vo.response.category.CategoryGroupResourceAssembler;
import com.jic.elearning.web.api.vo.response.category.CategoryResource;
import com.jic.elearning.web.api.vo.response.category.CategoryResourceAssembler;
import com.jic.tnw.web.api.vo.request.category.EditCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author lee5hx
 * @date 2018/3/21
 */
@RestController
@RequestMapping("/v1")
@Api(description = "分类管理", tags = {"G-课程模块-7"})
public class CategoryController {

    private static final Log LOGGER = LogFactory.getLog(CategoryController.class);
    private final LocaleMessageSourceService localeMessageSourceService;
    private final ObjectMapper mapper;
    private final CategoryService categoryService;

    private CategoryResourceAssembler categoryResourceAssembler = new CategoryResourceAssembler(CategoryController.class, CategoryResource.class);
    private CategoryGroupResourceAssembler categoryGroupResourceAssembler = new CategoryGroupResourceAssembler(CategoryController.class, CategoryGroupResource.class);


    @Autowired
    public CategoryController(
            LocaleMessageSourceService localeMessageSourceService,
            ObjectMapper mapper, CategoryService categoryService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.mapper = mapper;
        this.categoryService = categoryService;
    }


    @RequestMapping(value = "/categoryGroups", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回分类分组列表", notes = "返回分类分组列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")
    })
    public ResponseEntity<?> getCategoryGroups() throws Exception {
        List<CategoryGroup> list = categoryService.findCategoryGroups();
        List<CategoryGroupResource> resources = categoryGroupResourceAssembler.toResources(list);
        Resources<CategoryGroupResource> wrapped = new Resources<>(
                resources
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }


    @RequestMapping(value = "/categories/{groupId}", method = RequestMethod.GET, produces = "application/hal+json")
    @ApiOperation(value = "返回分类列表", notes = "返回分类列表", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "分组ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getCategories(@PathVariable Integer groupId) throws Exception {
        List<Category> categories = categoryService.findCategoryListByGroupId(groupId);
        List<CategoryResource> resources = categoryResourceAssembler.toResources(categories);
        Resources<CategoryResource> wrapped = new Resources<>(
                resources,
                linkTo(methodOn(CategoryController.class).addCategory(null, null)).withRel("post_category_add")
        );
        return new ResponseEntity<>(wrapped, HttpStatus.OK);
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ApiOperation(value = "新增分类", notes = "根据分组ID新增分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "addCategory", value = "新增分类", required = true, dataType = "AddCategory")
    })
    public ResponseEntity<?> addCategory(
            @Validated @RequestBody AddCategory addCategory,
            @AuthenticationPrincipal UserDetails user) throws Exception {
        AddCategoryDTO addCategoryDTO = new AddCategoryDTO();
        addCategoryDTO.setCode(addCategory.getCode());
        addCategoryDTO.setDescription(addCategory.getDescription());
        addCategoryDTO.setGroupId(Integer.valueOf(addCategory.getGroupId()));
        addCategoryDTO.setParentId(Integer.valueOf(addCategory.getParentId()));
        addCategoryDTO.setName(addCategory.getName());
        return new ResponseEntity<>(categoryResourceAssembler.toResource(categoryService.addCategory(addCategoryDTO)), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取分类", notes = "根据ID获取分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分类ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> getCategory(@PathVariable Integer id) throws Exception {
        CategoryResource resource = categoryResourceAssembler.toResource(categoryService.findById(id));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改分类", notes = "根据ID修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分类ID", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "editCategory", value = "修改分类", required = true, dataType = "EditCategory")
    })
    public ResponseEntity<?> editCategory(@PathVariable Integer id,
                                          @Validated @RequestBody EditCategory editCategory,
                                          @AuthenticationPrincipal UserDetails user) throws Exception {
        UpdateCategoryDTO updateCategoryDTO = new UpdateCategoryDTO();
        updateCategoryDTO.setId(id);
        updateCategoryDTO.setCode(editCategory.getCode());
        updateCategoryDTO.setName(editCategory.getName());
        updateCategoryDTO.setDescription(editCategory.getDescription());
        Category category = categoryService.updateCategory(updateCategoryDTO);
        CategoryResource resource = categoryResourceAssembler.toResource(category);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除分类", notes = "根据分组ID删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "分类ID", required = true, paramType = "path", dataType = "int"),
    })
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) throws Exception {
        CategoryResource resource = categoryResourceAssembler.toResource(categoryService.deleteById(id));
        return new ResponseEntity<>(resource, HttpStatus.NO_CONTENT);
    }


}
