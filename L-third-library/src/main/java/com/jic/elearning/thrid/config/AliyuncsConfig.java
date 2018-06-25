package com.jic.elearning.thrid.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by lee5hx on 2017/11/27.
 * <p>
 * --淘宝API配置
 */

@Configuration
public class AliyuncsConfig {

//    @Value("${taobao.api.url}")
//    private String url;
//
//    @Value("${taobao.api.app-key}")
//    private String appKey;
//
//    @Value("${taobao.api.app-secret}")
//    private String appSecret;
    @Value("${aliyunsms.api.accesskey-id}")
    private String appkey;
    @Value("${aliyunsms.api.accesskey-secret}")
    private String appSecret;
    @Value("${aliyunsms.api.product}")
    private String product;
    @Value("${aliyunsms.api.domain}")
    private String domain;
    @Bean
    public IAcsClient initDySmsApiClient() throws ClientException {


        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", appkey, appSecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }

    //todo email config

}
