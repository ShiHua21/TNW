package com.jic.elearning.service

import com.jic.elearning.web.api.Application
import com.jic.tnw.user.service.RoleService
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

// not mentioned by docs, but had to include this for Spock to startup the Spring context
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = DemoApplication.class)
//@ContextConfiguration
//@SpringBootConfiguration
//@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes=Application.class)
class RoleServiceSpec extends Specification {

//    @Autowired
//    UserService userService
    @Autowired
    DSLContext jooq

    @Autowired
    RoleService roleService

    def 'RoleService-getUrlRoleMap'() {

        expect: 'getUrlRoleMap'
        //userService.create(1,1,"11111")


        def user = roleService.getUrlRoleMap()
        //dsl.execute()
        1 == 1
    }




}
