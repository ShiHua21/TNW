package com.jic.elearning.thrid.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * Created by lee5hx on 2017/11/27.
 *
 * http://www.jianshu.com/p/4aaae49c8221 okhttp 配置
 */

@Configuration
public class OkHttpClientConfig {



//    @Bean
//    public OkHttpClient okHttpClient() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30,TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .sslSocketFactory(getTrustedSSLSocketFactory())
//                .hostnameVerifier(DO_NOT_VERIFY);
//        return builder.build();
//    }


    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }

}
