package com.jic.tnw.thrid.config;

import org.springframework.beans.factory.annotation.Value;


/**
 * Created by lee5hx on 2017/11/27.
 * <p>
 * --淘宝API配置
 */

//@Configuration
public class TaoBaoApiConfig {

    @Value("${taobao.api.url}")
    private String url;

    @Value("${taobao.api.app-key}")
    private String appKey;

    @Value("${taobao.api.app-secret}")
    private String appSecret;

//    @Bean
//    public TaobaoClient taobaoClient() {
//        TaobaoClient client = new DefaultTaobaoClient(
//                url,//OrderCatConfig.getTaobaoApiUrl(),
//                appKey,//OrderCatConfig.getTaobaoApiAppKey(),
//                appSecret//OrderCatConfig.getTaobaoApiAppSecret()
//        );
//        return client;
//    }
}
