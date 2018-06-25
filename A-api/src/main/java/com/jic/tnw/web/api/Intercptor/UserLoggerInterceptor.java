package com.jic.tnw.web.api.Intercptor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lee5hx on 2018/3/12.
 */
public class UserLoggerInterceptor implements HandlerInterceptor {


    private static final Log LOGGER = LogFactory.getLog(UserLoggerInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // String tt = getRequestPostStr(request);

//        System.out.println(request.getRequestURI());
//        System.out.println(IOUtils.toString(request.getInputStream(),"UTF-8"));

        HttpServletRequest requestCacheWrapperObject
                = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info(request);
        //String tt = getRequestPostStr(request);
       // System.out.println(tt);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        System.out.println(IOUtils.toString(responseWrapper.getContentInputStream(),"UTF-8"));
//        System.out.println(responseWrapper.getContentInputStream());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       // String tt = getRequestPostStr(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        System.out.println(IOUtils.toString(responseWrapper.getContentInputStream(),"UTF-8"));
//        System.out.println(responseWrapper.getContentInputStream());
        LOGGER.info(request);
    }


    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
}
