package com.jic.tnw.web.api.controller.v1;


import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.web.api.vo.request.RegisterAppStep1;
import com.jic.tnw.web.api.vo.response.HomeResource;
import com.jic.tnw.web.api.vo.response.IndexResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@Api(description = "API-INDEX", tags = {"A-index"})
public class IndexController {

    @Autowired
    private LocaleMessageSourceService localeMessageSourceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "index", notes = "API-入口")
    public ResponseEntity<?> index() throws Exception {

        IndexResource indexResource = new IndexResource();
        indexResource.setVersion("0.0.3");
        indexResource.setWelcome(localeMessageSourceService.getMessage("welcome"));
        indexResource.add(linkTo(methodOn(AuthController.class).register_app_step_1_1(new RegisterAppStep1())).withRel("register_app"));
        indexResource.add(linkTo(methodOn(AuthController.class).createAuthenticationToken(null, null)).withRel("login"));
        return ResponseEntity.ok(indexResource);
    }


    @RequestMapping(value = "/index_links", method = RequestMethod.GET)
    @ApiOperation(value = "index_links", notes = "API-idx_links获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> index_links(@AuthenticationPrincipal UserDetails user) throws Exception {
        HomeResource homeResource = new HomeResource();
        String authorities = user.getAuthorities().toString();




        homeResource.add(linkTo(methodOn(CommonController.class).getCommonOrgTree()).withRel("get_org_tree"));




        if (authorities.contains("ROLE_SUPER_ADMIN")) {
            homeResource.add(linkTo(methodOn(UserController.class).getUserByCurrent(null)).withRel("get_user_profile"));
            homeResource.add(linkTo(methodOn(UserController.class).getUsers(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )).withRel("get_user_list"));
            homeResource.add(linkTo(methodOn(PostController.class).getPost()).withRel("get_post_list"));
            homeResource.add(linkTo(methodOn(UserGroupController.class).getUserGroup()).withRel("get_user_group_list"));
            homeResource.add(linkTo(methodOn(RoleController.class).getRoles(null, null, null, null)).withRel("get_role_list"));
            homeResource.add(linkTo(methodOn(UserController.class).getCountByType()).withRel("get_user_count"));
            homeResource.add(linkTo(methodOn(CategoryController.class).getCategoryGroups()).withRel("get_category_groups_list"));
            homeResource.add(linkTo(methodOn(TagController.class).getTags(null, null)).withRel("get_course_tag"));
            homeResource.add(linkTo(methodOn(TagController.class).getTagGroups(null, null)).withRel("get_course_tagGroup"));
            homeResource.add(linkTo(methodOn(UserController.class).getUsers(
                    null,
                    null,
                    "ROLE_TEACHER",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )).withRel("get_teacher_user_list"));

            homeResource.add(linkTo(methodOn(CourseController.class).getCourseSets(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )).withRel("get_course_set_list"));
        }

        if (authorities.contains("ROLE_TEACHER")) {
            homeResource.add(linkTo(methodOn(UserController.class).getUserByCurrent(null)).withRel("get_user_profile"));
            homeResource.add(linkTo(methodOn(CourseController.class).getCourseSets(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            )).withRel("get_teacher_course_set_list"));
            homeResource.add(linkTo(methodOn(CourseController.class).addCourse(
                    null,
                    null
            )).withRel("post_add_course"));
            homeResource.add(linkTo(methodOn(CategoryController.class).getCategories(
                    1
            )).withRel("get_course_category_list"));

        }


        return ResponseEntity.ok(homeResource);
    }
}