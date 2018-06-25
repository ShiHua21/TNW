package com.jic.tnw.web.api.filter;

import org.apache.commons.io.IOUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lee5hx on 2018/3/20.
 */
public class JelUserActionLogFilter extends OncePerRequestFilter {


    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //System.out.println(request.getRequestURL().toString());
        //System.out.println(request.getRequestURI().toString());
        //<


        if (antPathMatcher.match("/v*/auth", request.getRequestURI().toString())) {
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
            filterChain.doFilter(request, responseWrapper);
            System.out.println(IOUtils.toString(responseWrapper.getContentInputStream(), "UTF-8"));
            responseWrapper.copyBodyToResponse();
        } else {
            filterChain.doFilter(request, response);
        }


        //System.out.println(IOUtils.toString(responseWrapper.getContentInputStream(),"UTF-8"));
//        System.out.println("1");
    }
}
