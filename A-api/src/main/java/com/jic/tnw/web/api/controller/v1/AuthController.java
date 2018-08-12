package com.jic.tnw.web.api.controller.v1;

import com.google.i18n.phonenumbers.Phonenumber;
import com.jic.tnw.common.exception.*;
import com.jic.tnw.common.secruity.jwt.JwtTokenUtil;
import com.jic.tnw.common.utils.PhoneUtils;
import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.thrid.domain.VerifyCode;
import com.jic.tnw.thrid.service.SMSCodeService;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.user.JelUser;
import com.jic.tnw.web.api.auth.AuthService;
import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.web.api.secruity.JwtAuthenticationResponse;
import com.jic.tnw.web.api.vo.request.CheckCode;
import com.jic.tnw.web.api.vo.request.MobileCheck;
import com.jic.tnw.web.api.vo.request.user.UserSetPassWord;
import com.jic.tnw.web.api.vo.response.CheckCodeResource;
import com.jic.tnw.web.api.vo.response.LoginResource;
import com.jic.tnw.web.api.vo.response.MobileCheckResource;
import com.jic.tnw.web.api.vo.response.user.JelUserResourceAssembler;
import com.jic.tnw.web.api.vo.response.user.UserResource;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/v1")
@Api(description = "登陆", tags = {"B-登陆认证"})
public class AuthController {
    private static final Log LOGGER = LogFactory.getLog(AuthController.class);

    private final AuthService authService;
    private final UserService userService;
    private final SMSCodeService smsCodeService;

    private final JwtTokenUtil jwtTokenUtil;

    private final JelUserResourceAssembler jelUserResourceAssembler = new JelUserResourceAssembler(UserController.class, UserResource.class);


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LocaleMessageSourceService localeMessageSourceService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    public AuthController(AuthService authService,
                          UserService userService,
                          SMSCodeService smsCodeService,
                          JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.userService = userService;
        this.smsCodeService = smsCodeService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ApiOperation(value = "登陆-验证", notes = "使用用户与密码登陆成功后返回Token.")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestParam String principal, @RequestParam String password, HttpServletRequest request) throws Exception {

        LoginResource loginResource = new LoginResource();
        String ip = getIpAddr(request);
        LOGGER.info(String.format("login Principal:[%s] Ip:[%s] ", principal, ip));

        UserDetails userDetails = authService.login(
                principal,
                password,
                getIpAddr(request));

        String name = userDetails.getUsername();
        User user = userService.findByPrincipal(name);

        String token = jwtTokenUtil.generateToken(userDetails);
        LOGGER.info(String.format("login token:[%s] ", token));
        loginResource.setStatus(HttpServletResponse.SC_OK);
        loginResource.setMessage("登陆成功！");
        loginResource.setToken(token);
//        loginResource.setLumobile(user.getLumobile());
        loginResource.setData(user);

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
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 // "***.***.***.***".length() // = 15
            if (ipAddress != null && ipAddress.length() > 15) {

                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }


    @RequestMapping(value = "auth/mobile/send/code", method = RequestMethod.POST)
    @ApiOperation(value = "sendCode(绑定手机：发送短信验证码)", notes = "绑定手机：发送短信验证码")
    public ResponseEntity<?> sendCode(@Validated @RequestParam String country, @RequestParam String phoneNo) throws Exception {

        MobileCheckResource mobileCheckResource = new MobileCheckResource();
        MobileCheck mobileCheck = new MobileCheck();
        mobileCheck.setPhoneNo(phoneNo);
        mobileCheck.setCountry(country);
        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(phoneNo, country);

        String countryCode = String.valueOf(number.getCountryCode());
        String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", countryCode));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", nationalNumber));

        if (userService.existsPhoneNo(phoneNo)) {
            throw new UserMobileExistsException();
        }

        String vcode = smsCodeService.sendCode(countryCode, nationalNumber, "AUTH_APP_REGISTER");
        mobileCheck.setVcode(vcode);
        mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
        mobileCheckResource.setMessage("发送短信验证码成功！");
        mobileCheckResource.setData(mobileCheck);
        return ResponseEntity.ok(mobileCheckResource);
    }


    @RequestMapping(value = {"auth/mobile/check/code"}, method = {RequestMethod.POST})
    @ApiOperation(value = "checkCode(绑定手机：绑定手机号+code)", notes = "绑定手机：绑定手机号+code")
    public ResponseEntity<?> checkCode(@Validated @RequestParam String id, @RequestParam String country, @RequestParam String phoneNo, @RequestParam String vcode) throws Exception {
        CheckCode checkCode = new CheckCode();
        if (userService.existsPhoneNo(checkCode.getPhoneNo())) {
            throw new UserMobileExistsException();
        } else {
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
            if (this.smsCodeService.verifyCode(verifyCode)) {
//            String name = principalUser.getUsername();
                JelUser jUser = userService.findById(id);
                JelUser jelUser = userService.userSetMobile(phoneNo, id);
                checkCodeResource.setStatus(200);
                checkCodeResource.setMessage("绑定手机号成功！");
                checkCodeResource.setData(verifyCode);
                this.jelUserResourceAssembler.toResource(jelUser);
            }

            return ResponseEntity.ok(checkCodeResource);
        }
    }

    /**
     * 校验该用户是否绑定手机
     */
    @RequestMapping(value = "auth/mobile", method = RequestMethod.POST)
    @ApiOperation(value = "验证-帐号是否绑定手机号", notes = "验证-帐号是否绑定手机号")
    public ResponseEntity<?> checkUsername(
            @RequestParam String principal, HttpServletRequest request) throws Exception {

        LoginResource loginResource = new LoginResource();

        User user = userService.findByPrincipal(principal);
        if (!StringUtils.isEmpty(user)) {

            String mobile = user.getLumobile();
            if (mobile != null && mobile.length() != 0) {
                loginResource.setStatus(HttpServletResponse.SC_OK);
                loginResource.setMessage("帐号已绑定手机号！");
                loginResource.setData(user);
            } else {
                throw new UserNoMobileExistsException2();
            }
        } else {
            throw new UserNoException();
        }
        return ResponseEntity.ok(loginResource);
    }

    @ApiIgnore
    @RequestMapping(value = "auth/mobile/check", method = RequestMethod.POST)
    @ApiOperation(value = "check(忘记密码：手机号校验)", notes = "忘记密码：手机号校验")
    public ResponseEntity<?> mobileCheck(@Validated @RequestParam String country, @RequestParam String phoneNo) throws Exception {

        MobileCheckResource mobileCheckResource = new MobileCheckResource();
        MobileCheck mobileCheck = new MobileCheck();
        mobileCheck.setCountry(country);
        mobileCheck.setPhoneNo(phoneNo);

        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(mobileCheck.getPhoneNo(), mobileCheck.getCountry());
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", String.valueOf(number.getCountryCode())));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", String.valueOf(number.getNationalNumber())));
//        校验手机号是否存在
        if (userService.existsPhoneNo(mobileCheck.getPhoneNo())) {
            mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
            mobileCheckResource.setMessage("该手机号校验通过！");
            mobileCheckResource.setData(new MobileCheckResource());
        } else {

            throw new UserNoMobileExistsException();
        }
        return ResponseEntity.ok(mobileCheckResource);
    }

    @RequestMapping(value = "auth/mobile/code", method = RequestMethod.POST)
    @ApiOperation(value = "check(忘记密码：发送短信)", notes = "忘记密码：发送短信")
    public ResponseEntity<?> mobileCheckToken(@Validated @RequestParam String country, @RequestParam String phoneNo) throws Exception {

        MobileCheckResource mobileCheckResource = new MobileCheckResource();
        MobileCheck mobileCheck = new MobileCheck();

        mobileCheck.setCountry(country);
        mobileCheck.setPhoneNo(phoneNo);

        Phonenumber.PhoneNumber number = PhoneUtils.getPhoneNumber(mobileCheck.getPhoneNo(), mobileCheck.getCountry());
        LOGGER.info(String.format("PhoneNumber-CountryCode:%s", String.valueOf(number.getCountryCode())));
        LOGGER.info(String.format("PhoneNumber-NationalNumber:%s", String.valueOf(number.getNationalNumber())));
        //校验手机号是否存在
        if (userService.existsPhoneNo(mobileCheck.getPhoneNo())) {
            //发送验证码
            String countryCode = String.valueOf(number.getCountryCode());
            String nationalNumber = String.valueOf(String.valueOf(number.getNationalNumber()));
            String vcode = smsCodeService.sendCode(countryCode, nationalNumber, "AUTH_APP_REGISTER");
            mobileCheck.setVcode(vcode.toString());
//            mobileCheckResource.setResidueTime(residueTime);
        } else {
            //该手机号码不存在！
            throw new UserNoMobileExistsException();
        }

        mobileCheckResource.setStatus(HttpServletResponse.SC_OK);
        mobileCheckResource.setMessage("验证码已发送！");
        mobileCheckResource.setData(mobileCheck);
        return ResponseEntity.ok(mobileCheckResource);
    }

    @RequestMapping(value = "auth/mobile/edit/pwd", method = RequestMethod.POST)
    @ApiOperation(value = "checkCode(忘记密码：更改密码+code)", notes = "忘记密码：更改密码+code")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true, dataType = "CheckCode")
    })
    public ResponseEntity<?> checkCode(@Validated @RequestParam String luuserid, @RequestParam String country, @RequestParam String phoneNo,
                                       @RequestParam String vcode, UserSetPassWord userSetPassWord) throws Exception {

        CheckCodeResource checkCodeResource = new CheckCodeResource();
        CheckCode checkCode = new CheckCode();
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

            if (!userSetPassWord.getQ2password().equals(userSetPassWord.getPassword())) {
                //2次密码不一致
                throw new TwoDifferentPasswordException();
            }

            if (userService.existsPhoneNo(phoneNo)) {
                //根据电话号码差user
                User user = userService.findByphoneNo(checkCode.getPhoneNo());
                JelUser jelUser = userService.editUserPwd(passwordEncoder.encode(userSetPassWord.getPassword()), user.getLuuserid());
                UserResource userResource = jelUserResourceAssembler.toResource(jelUser);

                checkCodeResource.setStatus(HttpServletResponse.SC_OK);
                checkCodeResource.setMessage("修改密码成功！");
                checkCodeResource.setData(checkCode);
            } else {
                //该手机号码不存在！
                throw new UserNoMobileExistsException();
            }

        }
        return ResponseEntity.ok(checkCodeResource);
    }


    @ApiIgnore
    @RequestMapping(value = "auth/refresh", method = RequestMethod.GET)
    @ApiOperation(value = "登陆-验证-刷新-令牌", notes = "使用旧token换取新的Token.")
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

}

