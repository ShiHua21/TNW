package com.jic.elearning.thrid.config;

import com.vaptcha.Vaptcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by lee5hx on 2017/11/27.
 *
 * http://www.jianshu.com/p/4aaae49c8221 okhttp 配置
 */

@Configuration
public class VaptchaConfig {



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



    @Value("${vaptcha.vid}")
    private String vid;
    @Value("${vaptcha.key}")
    private String key;


    @Bean
    public Vaptcha vaptchaClient() {
        //验证单元ID
        //String VID = "5a582c02a48617214c19eb3d";
        //String VID = "5a5822e6a48617214c19eb07";
        // 验证单元密钥
        //String KEY = "f35b016176d649a2af28036cc0b375eb";
        //String KEY = "6940042c97b646988f428ed0087059e5";
        Vaptcha vaptcha = new Vaptcha(vid,key);
        return vaptcha;
    }

}
