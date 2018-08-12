package com.jic.tnw.thrid.service;


import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.jic.tnw.thrid.domain.AliSMSHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class AliSMSServiceImpl implements SMSService {

    private final IAcsClient acsClient;

    private final RedisTemplate redisTemplate;

    private static final Log LOGGER = LogFactory.getLog(AliSMSServiceImpl.class);


    @Autowired
    AliSMSServiceImpl(IAcsClient acsClient, RedisTemplate redisTemplate) {

        this.acsClient = acsClient;
        this.redisTemplate = redisTemplate;

    }

    @Override
    public void send(AliSMSHeaders aliSMSHeaders) throws Exception{


        LOGGER.info(String.format("code=%s,PhoneNumber=%s",aliSMSHeaders.getCode(),aliSMSHeaders.getPhoneNumber()));
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号

        request.setPhoneNumbers(aliSMSHeaders.getPhoneNumber());
        //必填:短信签名-可在短信控制台中找到  建投alpha
        request.setSignName(aliSMSHeaders.getSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(aliSMSHeaders.getTemplateCode());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的,您的验证码为${code}"时,此处的值为
        String code = aliSMSHeaders.getCode();
//        request.setTemplateParam("{\"code\":\"1234\"}");
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("backID");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        LOGGER.info(String.format("getMessage=%s,getCode=%s",sendSmsResponse.getMessage(),sendSmsResponse.getCode()));

    }

}


