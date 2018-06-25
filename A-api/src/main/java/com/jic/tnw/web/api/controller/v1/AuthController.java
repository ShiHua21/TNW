package com.jic.tnw.web.api.controller.v1;

import com.google.i18n.phonenumbers.Phonenumber;
import com.jic.tnw.common.exception.UserMobileExistsException;
import com.jic.tnw.common.secruity.jwt.JwtTokenUtil;
import com.jic.tnw.common.utils.PhoneUtils;
import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.elearning.thrid.domain.VerifyCode;
import com.jic.elearning.thrid.service.SMSCodeService;
import com.jic.elearning.web.api.auth.AuthService;
import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import com.jic.elearning.web.api.vo.request.RegisterAppStep1;
import com.jic.elearning.web.api.vo.request.RegisterAppStep2;
import com.jic.elearning.web.api.vo.request.RegisterAppStep3;
import com.jic.elearning.web.api.vo.response.LoginResource;
import com.jic.elearning.web.api.vo.response.RegisterAppStep1Resource;
import com.jic.elearning.web.api.vo.response.RegisterAppStep2Resource;
import com.jic.elearning.web.api.vo.response.RegisterAppStep3Resource;
import com.jic.elearning.web.api.secruity.JwtAuthenticationRequest;
import com.jic.elearning.web.api.secruity.JwtAuthenticationResponse;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.web.api.vo.request.RegisterAppStep1;
import com.jic.tnw.web.api.vo.request.RegisterAppStep2;
import com.jic.tnw.web.api.vo.request.RegisterAppStep3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1")
@Api(description = "登陆 刷新令牌", tags = {"B-认证"})
public class AuthController {
    private static final Log LOGGER = LogFactory.getLog(AuthController.class);

    private final AuthService authService;
    private final UserService userService;
    private final SMSCodeService smsCodeService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LocaleMessageSourceService localeMessageSourceService;

    @Value("${jwt.header}")
    private String tokenHeader;
    //private final ProfileService profileService;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService,
                          SMSCodeService smsCodeService,
                          JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.userService = userService;
        this.smsCodeService = smsCodeService;
        //this.profileService = profileService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ApiOperation(value = "登陆-验证", notes = "使用用户与密码登陆成功后返回Token.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authenticationRequest", value = "验证实体", required = true, dataType = "JwtAuthenticationRequest")
    })
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,HttpServletRequest request) throws Exception {
        LoginResource loginResource = new LoginResource();
        String ip = getIpAddr(request);
        LOGGER.info(String.format("login Principal:[%s] Ip:[%s] ",
                authenticationRequest.getPrincipal(),
                ip
        ));
        final UserDetails userDetails = authService.login(
                authenticationRequest.getPrincipal(),
                authenticationRequest.getPassword(),
                getIpAddr(request));
        String token = jwtTokenUtil.generateToken(userDetails);
        LOGGER.info(String.format("login token:[%s] ", token));
        loginResource.setToken(token);
        loginResource.setRoles(userDetails.getAuthorities().toString());

        loginResource.add(linkTo(methodOn(AuthController.class).refreshAndGetAuthenticationToken(null)).withRel("refresh_token"));

        if(loginResource.getRoles().contains("ROLE_SUPER_ADMIN")){
            loginResource.add(linkTo(methodOn(OrgController.class).getOrgTree()).withRel("get_org_tree"));
            loginResource.add(linkTo(methodOn(UserController.class).getUsers(
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
            loginResource.add(linkTo(methodOn(PostController.class).getPost()).withRel("get_post_list"));
        }

        // Return the token
        return ResponseEntity.ok(loginResource);
    }


    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    @RequestMapping(value = "auth/refresh", method = RequestMethod.GET)
    @ApiOperation(value = "刷新-令牌", notes = "使用旧token换取新的Token.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT令牌", required = true, paramType = "header", dataType = "String")
    })
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @ApiIgnore
    @RequestMapping(value = "auth/register/app/step/1_1", method = RequestMethod.POST)
    @ApiOperation(value = "APP_注册_STEP_1_1-(手机号校验)", notes = "手机号校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerAppStep1", value = "手机号", required = true, dataType = "RegisterAppStep1")
    })
    public ResponseEntity<?> register_app_step_1_1(@Validated @RequestBody RegisterAppStep1 registerAppStep1) throws Exception {
        RegisterAppStep1Resource registerAppStep1Resource = new RegisterAppStep1Resource();

        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(registerAppStep1.getPhoneNo(), registerAppStep1.getCountry());
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", String.valueOf(number.getCountryCode())));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", String.valueOf(number.getNationalNumber())));
        //校验手机号是否存在
        if (userService.existsPhoneNo(registerAppStep1.getPhoneNo())) {
            throw new UserMobileExistsException();
        }
        registerAppStep1Resource.setCountry(String.valueOf(number.getCountryCode()));
        registerAppStep1Resource.setPhoneNo(String.valueOf(number.getNationalNumber()));
        registerAppStep1Resource.add(linkTo(methodOn(AuthController.class).register_app_step_1_2(null)).withRel("register_app_step_1_2"));
        return ResponseEntity.ok(registerAppStep1Resource);
    }
    @ApiIgnore
    @RequestMapping(value = "auth/register/app/step/1_2", method = RequestMethod.POST)
    @ApiOperation(value = "APP_注册_STEP_1_2-(发送注册短信验证码)", notes = "发送注册短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerAppStep1", value = "手机号", required = true, dataType = "RegisterAppStep1")
    })
    public ResponseEntity<?> register_app_step_1_2(@Validated @RequestBody RegisterAppStep1 registerAppStep1) throws Exception {
        RegisterAppStep1Resource registerAppStep1Resource = new RegisterAppStep1Resource();
        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(registerAppStep1.getPhoneNo(), registerAppStep1.getCountry());

        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", countryCode));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", nationalNumber));

        Integer residueTime = smsCodeService.sendCode(countryCode, nationalNumber, "AUTH_APP_REGISTER");
        registerAppStep1Resource.setResidueTime(residueTime);
        registerAppStep1Resource.add(linkTo(methodOn(AuthController.class).register_app_step_2(null)).withRel("register_app_step_2"));
        return ResponseEntity.ok(registerAppStep1Resource);
    }
    @ApiIgnore
    @RequestMapping(value = "auth/register/app/step/2", method = RequestMethod.POST)
    @ApiOperation(value = "APP_注册_STEP_2-(验证码校验)", notes = "验证码校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerAppStep2", value = "验证码", required = true, dataType = "RegisterAppStep2")
    })
    public ResponseEntity<?> register_app_step_2(@Validated @RequestBody RegisterAppStep2 registerAppStep2) throws Exception {
        RegisterAppStep2Resource registerAppStep2Resource = new RegisterAppStep2Resource();
        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(registerAppStep2.getPhoneNo(), registerAppStep2.getCountry());
        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));

        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCountryCode(countryCode);
        verifyCode.setPhoneNumber(nationalNumber);
        verifyCode.setCode(registerAppStep2.getVcode());
        verifyCode.setScene("AUTH_APP_REGISTER");
        if (smsCodeService.verifyCode(verifyCode)) {
            registerAppStep2Resource.setCountry(registerAppStep2.getCountry());
            registerAppStep2Resource.setPhoneNo(registerAppStep2.getPhoneNo());
            registerAppStep2Resource.setCountry(registerAppStep2.getCountry());
            registerAppStep2Resource.setVcode(registerAppStep2.getVcode());
        }
        registerAppStep2Resource.add(linkTo(methodOn(AuthController.class).register_app_step_3(null)).withRel("register_app_step_3"));
        return ResponseEntity.ok(registerAppStep2Resource);
    }
    @ApiIgnore
    @RequestMapping(value = "auth/register/app/step/3", method = RequestMethod.POST)
    @ApiOperation(value = "APP_注册_STEP_3-(密码设置)", notes = "密码设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerAppStep3", value = "密码", required = true, dataType = "RegisterAppStep3")
    })
    public ResponseEntity<?> register_app_step_3(@Validated @RequestBody RegisterAppStep3 registerAppStep3) throws Exception {
        RegisterAppStep3Resource registerAppStep3Resource = new RegisterAppStep3Resource();
        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(registerAppStep3.getPhoneNo(), registerAppStep3.getCountry());
        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCountryCode(countryCode);
        verifyCode.setPhoneNumber(nationalNumber);
        verifyCode.setCode(registerAppStep3.getVcode());
        verifyCode.setScene("AUTH_APP_REGISTER");
        if (smsCodeService.verifyCode(verifyCode)) {
            User addUser = new User();
            addUser.setMobile(registerAppStep3.getPhoneNo());
            addUser.setPassword(registerAppStep3.getPassword());
            //addUser = authService.register(addUser);
            smsCodeService.useCode(verifyCode);
            registerAppStep3Resource.setPhoneNo(addUser.getMobile());
            registerAppStep3Resource.setUserName(addUser.getUsername());
            registerAppStep3Resource.add(linkTo(methodOn(AuthController.class).createAuthenticationToken(null,null)).withRel("login_app"));
        }
        return ResponseEntity.ok(registerAppStep3Resource);
    }
    @ApiIgnore
    @RequestMapping(value = "auth/register/web", method = RequestMethod.POST)
    @ApiOperation(value = "WEB_注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "registerAppStep3", value = "密码", required = true, dataType = "RegisterAppStep3")
    })
    public ResponseEntity<?> register_web(@Validated @RequestBody RegisterAppStep3 registerAppStep3) throws Exception {


        return ResponseEntity.ok("");
    }
}


