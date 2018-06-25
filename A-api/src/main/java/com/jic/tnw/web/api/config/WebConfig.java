package com.jic.tnw.web.api.config;

import com.jic.elearning.web.api.Intercptor.UserLoggerInterceptor;
import com.jic.tnw.web.api.Intercptor.UserLoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created by lee5hx on 2017/10/18.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINESE);
        return slr;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


    @Bean
    public UserLoggerInterceptor userLoggerIntercptor() {
        UserLoggerInterceptor userLoggerInterceptor = new UserLoggerInterceptor();
        //lci.setParamName("lang");
        return userLoggerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(userLoggerIntercptor());

    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {


//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        //config.addAllowedOrigin("https://www.getpostman.com");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;


//        registry.addMapping("/api/**");
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST","PATCH","DELETE", "PUT", "OPTIONS")
                .allowedOrigins("*")
                .allowCredentials(true);
                //.allowedOrigins("*")
//                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
//                .allowedHeaders("Authorization");
                //.allowCredentials(false).maxAge(3600);



//        registry
//                .addMapping("/**")
//                .allowedHeaders("Authorization");
    }

}
