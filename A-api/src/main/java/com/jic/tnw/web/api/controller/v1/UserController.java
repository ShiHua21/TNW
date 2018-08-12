package com.jic.tnw.web.api.controller.v1;

import com.google.i18n.phonenumbers.Phonenumber;
import com.jic.tnw.common.exception.*;
import com.jic.tnw.common.utils.PhoneUtils;
import com.jic.tnw.db.mysql.tables.pojos.*;
import com.jic.tnw.thrid.domain.VerifyCode;
import com.jic.tnw.thrid.service.SMSCodeService;
import com.jic.tnw.user.service.DwOrgcircleDynamicService;
import com.jic.tnw.user.service.OrgService;
import com.jic.tnw.user.service.PostService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.web.api.service.FileService;
import com.jic.tnw.web.api.vo.request.CheckCode;
import com.jic.tnw.web.api.vo.request.MobileCheck;
import com.jic.tnw.web.api.vo.request.user.UserSetPassWord;
import com.jic.tnw.web.api.vo.response.*;
import com.jic.tnw.web.api.vo.response.user.JelUserResourceAssembler;
import com.jic.tnw.web.api.vo.response.user.UserResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//@ApiIgnore
@RestController
@RequestMapping("/v1")
@Api(description = "用户管理", tags = {"C-用户模块-1"})
public class UserController {

    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    private final LocaleMessageSourceService localeMessageSourceService;

    private final UserService userService;

    private final FileService fileService;

    private final PostService postService;

    private final DwOrgcircleDynamicService dwOrgcircleDynamicService;

    private final SMSCodeService smsCodeService;
    private final OrgService orgService;
    private final JelUserResourceAssembler jelUserResourceAssembler = new JelUserResourceAssembler(UserController.class, UserResource.class);


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserController(
            LocaleMessageSourceService localeMessageSourceService,
            UserService userService,
            SMSCodeService smsCodeService,
            DwOrgcircleDynamicService dwOrgcircleDynamicService,
            PostService postService,
            FileService fileService,
            OrgService orgService) {
        this.localeMessageSourceService = localeMessageSourceService;
        this.userService = userService;
        this.postService = postService;
        this.fileService = fileService;
        this.smsCodeService = smsCodeService;
        this.dwOrgcircleDynamicService = dwOrgcircleDynamicService;
        this.orgService = orgService;
    }


    /**
     * WO个人首页
     */
    @RequestMapping(value = "/user/page/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "我的：个人首页", notes = "我的：个人首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
//            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "int")
    })
    public ResponseEntity<?> getUserPageById(@PathVariable String id) throws Exception {
        JelUser jelUser = userService.findById(id);
        User user = jelUser.getUser();
        if (user == null) {
            throw new UserNotExistsException();
        }
        UserPageResource userPage = new UserPageResource();

        if (user.getLuorgid() == "ROLE_DEPARTMENT_ADMIN" || user.getLuorgid() == "ROLE_SUPER_ADMIN") { //部门管理员 //admin
            userPage.add(linkTo(methodOn(UserController.class).getUserActivity(userPage.getLuuserid(), null, null)).withRel("patch_get_user_activity"));
            userPage.add(linkTo(methodOn(UserController.class).getUserDynamic(userPage.getLuuserid(), null, null)).withRel("patch_get_user_dynamic"));
        }

        userPage.setStatus(200);
        userPage.setMessage("我的个人首页");
        userPage.setData(user);

        return ResponseEntity.ok(userPage);
    }


    /**
     * 我的留言
     */
    @RequestMapping(value = "/user/self/message", method = RequestMethod.GET)
    @ApiOperation(value = "我的：我的留言", notes = "我的：我的留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getSelfMessage(@RequestParam String id, Pageable pageable, PagedResourcesAssembler assembler) throws Exception {

        SelfMessageResource mobileCheckResource = new SelfMessageResource();

//        Map<String, Object> conditionMap = new HashMap<>(5);
//        if (!StringUtils.isEmpty(id)) {
//            conditionMap.put("id", id);
//        }

        Page<DwMyMessage> page = userService.selfMessagePageable(pageable, id);

        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
            throw new UserMessageExistsException();
        }
        mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
        mobileCheckResource.setMessage("我的：我的留言");
        mobileCheckResource.setData(page);
        return ResponseEntity.ok(mobileCheckResource);
    }

    /**
     * 删除我的留言
     */
    @RequestMapping(value = "/user/delete/self/message", method = RequestMethod.POST)
    @ApiOperation(value = "我的：删除我的留言", notes = "我的：删除我的留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "我的留言id", dataType = "int", paramType = "query"),
    })
    public ResponseEntity<?> deleteSelfMessage(@RequestParam Integer id) throws Exception {

        SelfMessageResource mobileCheckResource = new SelfMessageResource();

        DwMyMessage myMessage = userService.findMyMessageById(id);

        if (StringUtils.isEmpty(myMessage)) {
            throw new UserMessageExistsException();
        }

        String ludelete = myMessage.getLudelete();
        if (ludelete.equals("0")) {
            DwMyMessage message = userService.deleteMyMessage(ludelete, id);

            mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
            mobileCheckResource.setMessage("我的：删除我的留言");
            mobileCheckResource.setData(new DwMyMessage());
        }
        return ResponseEntity.ok(mobileCheckResource);
    }


    /**
     * 他人留言
     */
    @RequestMapping(value = "/user/other/message", method = RequestMethod.GET)
    @ApiOperation(value = "我的：他人留言", notes = "我的：他人留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getOtherMessage(@RequestParam String id, Pageable pageable, PagedResourcesAssembler
            assembler) throws Exception {

        SelfMessageResource mobileCheckResource = new SelfMessageResource();

        Map<String, Object> conditionMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(id)) {
            conditionMap.put("id", id);
        }

        Page<DwOtherMessage> page = userService.otherMessagePageable(pageable, id);

        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
            throw new UserMessageExistsException();
        }
        mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
        mobileCheckResource.setMessage("我的：他人留言");
        mobileCheckResource.setData(page);
        return ResponseEntity.ok(mobileCheckResource);
    }


    /**
     * 删除他人留言
     */
    @RequestMapping(value = "/user/delete/other/message", method = RequestMethod.GET)
    @ApiOperation(value = "我的：删除他人留言", notes = "我的：删除他人留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", paramType = "query"),
    })
    public ResponseEntity<?> deleteOtherMessage(@RequestParam Integer id) throws Exception {

        SelfMessageResource mobileCheckResource = new SelfMessageResource();

        DwOtherMessage otherMessage = userService.findOtherMessageById(id);

        if (StringUtils.isEmpty(otherMessage)) {
            throw new UserMessageExistsException();
        }

        String ludelete = otherMessage.getLudelete();
        if (ludelete.equals("0")) {
            DwOtherMessage message = userService.deleteOtherMessage(ludelete, id);

            mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
            mobileCheckResource.setMessage("我的：删除他人留言");
            mobileCheckResource.setData(new DwOtherMessage());
        }
        return ResponseEntity.ok(mobileCheckResource);
    }

    /**
     * 我的：我的动态
     */
    @RequestMapping(value = "/user/dynamic", method = RequestMethod.GET)
    @ApiOperation(value = "我的：我的动态", notes = "我的：我的动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getUserDynamic(@RequestParam String id, Pageable pageable, PagedResourcesAssembler
            assembler) throws Exception {

        OrgcircleDetailResource orgcircleDetail = new OrgcircleDetailResource();

        Map<String, Object> conditionMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(id)) {
            conditionMap.put("id", id);
        }

        Page<DwOrgcircleDynamic> page = userService.findUserDynamicById(pageable, id);

        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
            throw new UserMessageExistsException();
        }
        orgcircleDetail.setStatus(HttpServletResponse.SC_OK);
        orgcircleDetail.setMessage("我的：我的动态");
        orgcircleDetail.setData(page);
        return ResponseEntity.ok(orgcircleDetail);
    }

    /**
     * 我的：我的活动
     */
    @RequestMapping(value = "/user/activity", method = RequestMethod.GET)
    @ApiOperation(value = "我的：我的活动", notes = "我的：我的活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getUserActivity(@RequestParam String id, Pageable pageable, PagedResourcesAssembler
            assembler) throws Exception {

        OrgcircleActivityResource activity = new OrgcircleActivityResource();

        Map<String, Object> conditionMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(id)) {
            conditionMap.put("id", id);
        }

        Page<DwOrgcircleActivity> page = userService.findUserActivityById(pageable, id);

        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
            throw new UserMessageExistsException();
        }
        activity.setStatus(HttpServletResponse.SC_OK);
        activity.setMessage("我的：我的活动");
        activity.setData(page);
        return ResponseEntity.ok(activity);
    }


    /**
     * 编辑活动
     */
    @RequestMapping(value = "/user/update/activity", method = RequestMethod.GET)
    @ApiOperation(value = "我的：编辑活动", notes = "我的：编辑活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "活动id", dataType = "int", paramType = "query"),
    })
    public ResponseEntity<?> editUserActivity(@RequestParam Integer id, DwOrgcircleActivity dwactivity) throws Exception {

        OrgcircleActivityResource activity = new OrgcircleActivityResource();

        DwOrgcircleActivity orgcircleActivity = userService.findById(id);

        if (!StringUtils.isEmpty(orgcircleActivity)) {

            DwOrgcircleActivity updateActivity = userService.updateActivityById(dwactivity, id);
            activity.setStatus(HttpServletResponse.SC_OK);
            activity.setMessage("我的：我的活动");
            activity.setData(updateActivity);
        }

//
//        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
//            throw new UserMessageExistsException();
//        }
        return ResponseEntity.ok(activity);
    }


    /**
     * 已参与活动
     */
    @RequestMapping(value = "/user/old/activity", method = RequestMethod.GET)
    @ApiOperation(value = "我的：已参与活动", notes = "我的：已参与活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "分页大小", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataType = "String", paramType = "query"),
    })
    public ResponseEntity<?> getOldUserActivity(@RequestParam String id, Pageable pageable, PagedResourcesAssembler
            assembler) throws Exception {

        OrgcircleActivityResource activity = new OrgcircleActivityResource();

        Map<String, Object> conditionMap = new HashMap<>(5);
        if (!StringUtils.isEmpty(id)) {
            conditionMap.put("id", id);
        }

        Page<DwOldActivity> page = userService.findOldUserActivityById(pageable, id);

        if (page.getTotalElements() == 0 && page.getTotalPages() == 0) {
            throw new ParticipatingActivitiesException();
        }


        activity.setStatus(HttpServletResponse.SC_OK);
        activity.setMessage("我的：已参与活动");
        activity.setData(page);
        return ResponseEntity.ok(activity);
    }

    /**
     * 删除动态
     */
    @RequestMapping(value = "/user/delete/dynamic", method = RequestMethod.POST)
    @ApiOperation(value = "我的：删除动态", notes = "我的：删除动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "动态id", dataType = "int", paramType = "query"),
    })
    public ResponseEntity<?> deleteDynamic(@RequestParam Integer id) throws Exception {

        OrgcircleDetailResource orgcircleDetail = new OrgcircleDetailResource();


        DwOrgcircleDynamic orgcircle = userService.findDynamicById(id);

        if (StringUtils.isEmpty(orgcircle)) {
            throw new UserMessageExistsException();
        }

        String ludelete = orgcircle.getLudelete();
        if (ludelete.equals("0")) {
            //
            DwOrgcircleDynamic dwOrgcircleDetail = userService.deleteDynamic(ludelete, id);


            orgcircleDetail.setStatus(HttpServletResponse.SC_OK);
            orgcircleDetail.setMessage("我的：删除动态");
            orgcircleDetail.setData(dwOrgcircleDetail);
        }
        return ResponseEntity.ok(new DwOrgcircleDynamic());
    }


    //-============------------------------------

    /**
     * 我的：更改手机号
     */
    @RequestMapping(value = "user/mobile/send/code", method = RequestMethod.POST)
    @ApiOperation(value = "我的：发送短信验证码", notes = "我的：发送短信验证码")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> sendCode(@Validated @RequestParam String country, @RequestParam String phoneNo) throws
            Exception {

        MobileCheckResource mobileCheckResource = new MobileCheckResource();
        MobileCheck mobileCheck = new MobileCheck();
        mobileCheck.setPhoneNo(phoneNo);
        mobileCheck.setCountry(country);
        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(phoneNo, country);

        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", countryCode));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", nationalNumber));

        String vcode = smsCodeService.sendCode(countryCode, nationalNumber, "AUTH_APP_REGISTER");
        mobileCheck.setVcode(vcode);
        mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
        mobileCheckResource.setMessage("发送短信验证码成功！");
        mobileCheckResource.setData(mobileCheck);
        return ResponseEntity.ok(mobileCheckResource);
    }


    @RequestMapping(value = "user/mobile/check/code", method = RequestMethod.POST)
    @ApiOperation(value = "我的：更换手机号+code", notes = "我的：更换手机号+code")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> checkCode(@Validated @RequestParam String id, @RequestParam String country, @RequestParam String phoneNo,
                                       @RequestParam String vcode, @AuthenticationPrincipal UserDetails principalUser) throws Exception {

        CheckCode checkCode = new CheckCode();
        if (userService.existsPhoneNo(checkCode.getPhoneNo())) {
            throw new UserMobileExistsException();
        }

        CheckCodeResource checkCodeResource = new CheckCodeResource();
        checkCode.setCountry(country);
        checkCode.setPhoneNo(phoneNo);
        checkCode.setVcode(vcode);

        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(checkCode.getPhoneNo(), checkCode.getCountry());
        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCountryCode(countryCode);
        verifyCode.setPhoneNumber(nationalNumber);
        verifyCode.setCode(checkCode.getVcode());
        verifyCode.setScene("AUTH_APP_REGISTER");
        if (smsCodeService.verifyCode(verifyCode)) {

            JelUser jUser = userService.findById(id);
            //根据id绑定手机号
            JelUser jelUser = userService.userSetMobile(jUser.getUser().getLumobile(), id);

            checkCodeResource.setStatus(HttpServletResponse.SC_OK);
            checkCodeResource.setMessage("更换手机号成功！");
            checkCodeResource.setData(verifyCode);
            UserResource resource = jelUserResourceAssembler.toResource(jelUser);
        }
        return ResponseEntity.ok(checkCodeResource);
    }


    @RequestMapping(value = "/user/{id}/editPassWord", method = RequestMethod.POST)
    @ApiOperation(value = "根据id修改用户密码", notes = "根据id修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
    })
    public ResponseEntity<?> editUserPassWord(UserSetPassWord userSetPassWord, @PathVariable String id) throws
            Exception {

        PwdCheckResource editPwd = new PwdCheckResource();

        if (!userSetPassWord.getQ2password().equals(userSetPassWord.getPassword())) {
            //2次密码不一致
            throw new TwoDifferentPasswordException();
        }

        JelUser User = userService.oldUserPwd(passwordEncoder.encode(userSetPassWord.getQ2password()), id);
        String oldUserPwd = User.getUser().getLupwd();
        String pwd = User.getUser().getLupwd();
        if (oldUserPwd == pwd) {
            JelUser jelUser = userService.editUserPwd(passwordEncoder.encode(userSetPassWord.getPassword()), id);
            //        UserResource userResource = jelUserResourceAssembler.toResource(jelUser);
            editPwd.setStatus(HttpServletResponse.SC_OK);
            editPwd.setMessage("修改密码成功！");
            editPwd.setData(new PwdCheckResource());
        } else {
            throw new TwoDifferentPasswordException();
        }
        return ResponseEntity.ok(editPwd);
    }
//    ---------------

    /**
     * 设置头像
     */
    @RequestMapping(value = "/user/{id}/setAvatar", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户头像", notes = "设置用户头像", produces = "application/hal+json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "String"),
    })
    public ResponseEntity<?> setAvatar2(
            @PathVariable String id,
            @RequestPart String group,
            @RequestPart MultipartFile file,
            @AuthenticationPrincipal UserDetails user) throws Exception {
        LOGGER.debug(String.format("upload file group=%s file.size=%d", group, file.getSize()));

        UserListResource userListResource = new UserListResource();

        Integer uId = Integer.parseInt(user.getUsername());
        LOGGER.debug(String.format("userId=%d,upload file group=%s file.size=%d",
                uId,
                group,
                file.getSize()));
        //检查分组编码
        Optional<FileStoreGroup> opt = fileService.getFileGroupByCode(group);
        if (!opt.isPresent()) {
            throw new FileGroupCodeErrorException();
        }
        //判断是否为图片
        String image = "image";
        //bmp jpg jpeg gif png ico
        String[] imageExtensions = new String[]{"bmp", "jpg", "jpeg", "gif", "png", "ico"};
        String extension;
        if (file.getContentType().contains(image)) {
            extension = file.getOriginalFilename().split("\\.", -1)[1];
            if (!Arrays.asList(imageExtensions).contains(extension)) {
                throw new ImageTypeSupportErrorException();
            }
        } else {
            throw new UploadFileTypeErrorException();
        }

        JelUser jUser = userService.findById(id);
        User users = jUser.getUser();
        if (!StringUtils.isEmpty(users)) {


//        Integer userId = Integer.parseInt(uId);
            FileStore fileStore = fileService.uploadFile(uId, opt.get(), file);
            String url = fileStore.getFileUri();
            JelUser userPortrait = userService.portraitFile(uId.toString(), url);
            userListResource.setStatus(HttpServletResponse.SC_OK);
            userListResource.setMessage("修改头像成功！");
            userListResource.setData(userPortrait);
        }
        return ResponseEntity.ok(userListResource);
    }

/**
 * 退出登陆
 */

//
//    @ApiIgnore
//    @RequestMapping(value = "auth/refresh", method = RequestMethod.GET)
//    @ApiOperation(value = "登陆-验证-刷新-令牌", notes = "使用旧token换取新的Token.")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")
//    })
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(
//            HttpServletRequest request) throws AuthenticationException {
//        String token = request.getHeader(tokenHeader);
//        String refreshedToken = authService.refresh(token);
//        if (refreshedToken == null) {
//            return ResponseEntity.badRequest().body(null);
//        } else {
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        }
//    }
}
