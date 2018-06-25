package com.jic.elearning.thrid.service;



import com.jic.elearning.thrid.domain.AliSMSForRedisParameter;
import com.jic.elearning.thrid.domain.AliSMSHeaders;
import com.jic.elearning.thrid.domain.VerifyCode;
import com.jic.elearning.thrid.exception.VerifyCodeErrorException;
import com.jic.elearning.thrid.exception.VerifyCodeInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class SMSCodeServiceImpl implements SMSCodeService {



    private final SMSService smsService;
    private final RedisTemplate redisTemplate;


    @Autowired
        SMSCodeServiceImpl(SMSService smsService, RedisTemplate redisTemplate) {
            this.smsService = smsService;
            this.redisTemplate = redisTemplate;
        }
    @Override
    public Integer sendCode(String countryCode,String phoneNumber,String scene) throws Exception{
        //String key = sendCodeHeaders.getScene()+"_"+sendCodeHeaders.getPhoneNumber();
        String key = String.format("%s_%s_%s",scene,countryCode,phoneNumber);

        AliSMSHeaders aliSMSHeaders = new AliSMSHeaders();
        aliSMSHeaders.setScene(scene);
        aliSMSHeaders.setPhoneNumber(phoneNumber);
        aliSMSHeaders.setSignName("建投alpha");
        aliSMSHeaders.setTemplateCode("SMS_121165037");
        String code = getCode();
        aliSMSHeaders.setCode(code);
        Integer residueTime ;
        Object obj = redisTemplate.opsForValue().get(key);
        if(obj!=null){
            AliSMSForRedisParameter aliSMSForRedisParameter = (AliSMSForRedisParameter) obj;
            Date newDate = new Date();
            Date date = aliSMSForRedisParameter.getCreateTime();
            Integer interval =(int)(newDate.getTime() - date.getTime())/1000;
            if (interval<60){
                Integer timeWar = 60 - interval;
                residueTime = timeWar;
                return residueTime;
            }
        }
        AliSMSForRedisParameter aliSMSForRedisParameter = new AliSMSForRedisParameter();
        aliSMSForRedisParameter.setCode(code);
        aliSMSForRedisParameter.setCreateTime(new Date());
        aliSMSForRedisParameter.setUsed(Boolean.FALSE);
        residueTime = 60;
        redisTemplate.opsForValue().set(key,aliSMSForRedisParameter,15,MINUTES);
        smsService.send(aliSMSHeaders);
        return residueTime;
    }
    @Override
    public Boolean verifyCode(VerifyCode verifyCode) {
        String key = String.format("%s_%s_%s",verifyCode.getScene(),verifyCode.getCountryCode(),verifyCode.getPhoneNumber());
        //String key = verifyCode.getScene()+"_"+verifyCode.getPhoneNumber();
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj != null){
            AliSMSForRedisParameter aliSMSForRedisParameter = (AliSMSForRedisParameter) obj;
            if (aliSMSForRedisParameter.getUsed() == false && verifyCode.getCode().equals(aliSMSForRedisParameter.getCode())){
                return true;
            }else {
                throw new VerifyCodeErrorException();
            }
        }else {

            throw new VerifyCodeInvalidException();
        }

    }
    public static String getCode(){
        String code="";
        for(int i=0;i<6;i++){
            code+=(int)Math.floor(Math.random()*10);
        }
        return code;
    }

    @Override
    public void useCode(VerifyCode verifyCode) {
        String key = String.format("%s_%s_%s",verifyCode.getScene(),verifyCode.getCountryCode(),verifyCode.getPhoneNumber());
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj != null){
            AliSMSForRedisParameter aliSMSForRedisParameter = (AliSMSForRedisParameter) obj;
            aliSMSForRedisParameter.setUsed(Boolean.TRUE);
            redisTemplate.opsForValue().set(key,aliSMSForRedisParameter);
        }
    }
}
