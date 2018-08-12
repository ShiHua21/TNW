package com.jic.tnw.web.api.secruity;

import com.jic.tnw.db.mysql.tables.pojos.User;
import com.jic.tnw.user.service.UserService;
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
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
//        JelUser jelUser = userService.findById(username); //token传值 bu
        User user = userService.findByPrincipal(principal);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", principal));
        }
        if (user.getLuactive() == true) {
            throw new UsernameNotFoundException(String.format("User is Lock with username '%s'.", principal));
        }
        return JwtUserFactory.create(user);
    }
}
