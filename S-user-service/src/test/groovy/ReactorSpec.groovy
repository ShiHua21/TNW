
//import me.gosimple.nbvcxz.Nbvcxz
import reactor.core.publisher.Flux
import spock.lang.Specification

// not mentioned by docs, but had to include this for Spock to startup the Spring context
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = DemoApplication.class)
//@ContextConfiguration
//@SpringBootConfiguration
//@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes=Application.class)
class ReactorSpec extends Specification {

//    @Autowired
//    UserService userService


    def 'Flux test'() {

        expect: 'getUrlRoleMap'
        def list = new ArrayList();
        list.add("1")
        list.add("2")
        list.add("3")
        //Flux.fromIterable(list).
        1==1

    }




}
