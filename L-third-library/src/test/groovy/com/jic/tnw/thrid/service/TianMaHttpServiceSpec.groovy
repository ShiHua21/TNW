package com.jic.tnw.thrid.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

//@ContextConfiguration(classes = [
//        OkHttpClientConfig.class,
//        TianmaProperties.class,
//        TianMaHttpService.class,
//        TianMaHttpServiceImpl.class,
//        StringRedisTemplate.class,
//        RedisConnectionFactory.class
//
//], loader = SpringBootContextLoader.class
//)
//@SpringBootTest


//@ScanScopedBeans
@SpringBootTest
//@ContextConfiguration(classes = [JacksonObjectMapperConfig.class])
@Import(JacksonObjectMapperConfig.class)
class TianMaHttpServiceSpec extends Specification {

//    @Autowired
//    TianMaHttpService tianMaHttpService



    @Autowired
    ScheduleHttpService scheduleHttpService

    def "Schedule runOneTask"() {

        expect:
        1 == 1

        when:
        def dd = scheduleHttpService.runOneTask("OC2-GR-JOB1","sendGoodsTaskDetail","lee5hx123")
        System.out.println(dd.toString())
        then:
        dd.total == 1
        dd.rows.get(0).getOrderId() == "27269060"
    }
}
