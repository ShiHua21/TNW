package com.jic.tnw.web.api.secruity;

import com.jic.tnw.user.service.UserService;
import com.jic.tnw.user.service.dto.user.JelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * UserServiceImpl
 *
 *
 * @author lee5hx
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JelUser jelUser = userService.findById(Integer.valueOf(username));
        if (jelUser == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        if (jelUser.getUser().getLocked() == true) {
            throw new UsernameNotFoundException(String.format("User is Lock with username '%s'.", username));
        }
        return JwtUserFactory.create(jelUser.getUser());
    }
}
