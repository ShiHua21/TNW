package com.jic.tnw.web.api.config;

import com.jic.tnw.user.service.UserService;
import com.jic.tnw.web.api.filter.JelUserActionLogFilter;
import com.jic.tnw.web.api.secruity.JelAuthenticationProvider;
import com.jic.tnw.web.api.secruity.JwtAuthenticationEntryPoint;
import com.jic.tnw.web.api.secruity.JwtAuthenticationTokenFilter;
import com.jic.tnw.web.api.secruity.MyAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//import com.jic.tnw.web.secruity.*;

/**
 * 配置整体站点安全
 */
@Configuration
@EnableWebSecurity
public class
ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
//
//    @Autowired
//    private RoleService roleService;


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {



        JelAuthenticationProvider jelAuthenticationProvider = new JelAuthenticationProvider();
        jelAuthenticationProvider.setUserService(userService);
        jelAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        authenticationManagerBuilder
                .authenticationProvider(jelAuthenticationProvider)
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());




    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public JelUserActionLogFilter jelRequestLoggingFilterBean() throws Exception {
        return new JelUserActionLogFilter();
    }


//    @Bean
//    public FilterInvocationSecurityMetadataSource mySecurityMetadataSource() {
//        MyFilterInvocationSecurityMetadataSource securityMetadataSource = new MyFilterInvocationSecurityMetadataSource();
//        Map<String, String[]> map = roleService.getUrlRoleMap();
//        //swagger2
//        map.put("/", new String[]{"ROLE_ANONYMOUS"});
//        map.put("/favicon.ico", new String[]{"ROLE_ANONYMOUS"});
//        map.put("/swagger-ui.html", new String[]{"ROLE_ANONYMOUS"});
//        map.put("/webjars/**", new String[]{"ROLE_ANONYMOUS"});
//        map.put("/v2/api-docs**", new String[]{"ROLE_ANONYMOUS"});
//        map.put("/swagger-resources/**", new String[]{"ROLE_ANONYMOUS"});
//        securityMetadataSource.setUrlRoleMap(map);
//        return securityMetadataSource;
//    }

    @Bean
    public AccessDecisionManager myAccessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
//                        fsi.setSecurityMetadataSource(mySecurityMetadataSource());
                        fsi.setAccessDecisionManager(myAccessDecisionManager());
                        fsi.setPublishAuthorizationSuccess(true);
                        return fsi;
                    }
                });
        // 添加JWT filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), BasicAuthenticationFilter.class);
        httpSecurity
                .addFilterBefore(jelRequestLoggingFilterBean(),JwtAuthenticationTokenFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();

    }


}
