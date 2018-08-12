package com.jic.tnw.web.api.auth;


import com.jic.tnw.common.secruity.jwt.JwtTokenUtil;
import com.jic.tnw.common.secruity.jwt.JwtUser;
import com.jic.tnw.user.service.UserService;
import com.jic.tnw.web.api.config.LocaleMessageSourceService;
import com.jic.tnw.web.api.secruity.JelAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private LocaleMessageSourceService localeMessageSourceService;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public UserDetails login(String principal, String password,String ip) {
        //UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        JelAuthenticationToken upToken = new JelAuthenticationToken(principal,password);
        upToken.setIp(ip);
        // 进行安全认证
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成token
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //jwtTokenUtil.generateToken(userDetails)
        return userDetails;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetTime())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
