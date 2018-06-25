package com.jic.tnw.web.api.secruity;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.user.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lee5hx
 * @date 2018/1/9
 * JIC-EL 登陆逻辑
 */
public class JelAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JelAuthenticationToken jelAuthenticationToken = (JelAuthenticationToken) authentication;
        String password = (String) jelAuthenticationToken.getCredentials();
        String principal = (String) jelAuthenticationToken.getPrincipal();
        User user = userService.findByPrincipal(principal);
        if(user == null){
            throw new BadCredentialsException("");
        }
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("");
        }
        UserDetails userDetails = JwtUserFactory.create(user);
        JelAuthenticationToken authenticationToken = new JelAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setIp(jelAuthenticationToken.getIp());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JelAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}