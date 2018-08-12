package com.jic.tnw.web.api.secruity;

import com.jic.tnw.common.secruity.jwt.JwtUser;
import com.jic.tnw.db.mysql.tables.pojos.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

final class JwtUserFactory {

    private JwtUserFactory() {
    }

    static JwtUser create(User user) {


        LocalDateTime ldt = user.getLastPasswordResetTime();
        //Date Lastpasswordresettime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());


        return new JwtUser(
                user.getLuuserid(),
                user.getLumobile(),
                String.valueOf(user.getLuuserid()),
                user.getLupwd(),
                stringToGrantedAuthorities(user.getLuorgid()),
                ldt
        );
    }

    private static Set<GrantedAuthority> stringToGrantedAuthorities(String authorities) {
        return Arrays.asList(authorities.split(",")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}

