package com.jic.tnw.service

import com.jic.tnw.db.repository.UserRepository
import com.jic.tnw.user.service.RoleService
import com.jic.tnw.web.api.Application
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
class UserServiceSpec extends Specification {

//    @Autowired
//    UserService userService
    @Autowired
    DSLContext jooq

    @Autowired
    UserRepository userRepository

    @Autowired
    RoleService roleService

    def 'user-generateUserName'() {

        expect: 'generateUserName'
        def ff = userRepository.generateUserName()


        //def user = roleService.getUrlRoleMap()
        //dsl.execute()
        ff=="123123"
    }




}
