package com.jic.tnw.service
//import me.gosimple.nbvcxz.Nbvcxz
import spock.lang.Specification

// not mentioned by docs, but had to include this for Spock to startup the Spring context
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = DemoApplication.class)
//@ContextConfiguration
//@SpringBootConfiguration
//@RunWith(SpringRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes=Application.class)
class PassWordSpec extends Specification {

//    @Autowired
//    UserService userService


    def 'password test'() {

        expect: 'getUrlRoleMap'

//        Nbvcxz nbvcxz = new Nbvcxz();
//        def rt = nbvcxz.estimate("jiayoua")


        double singleGuess = 0.01;
        double numAttackers = 100; // number of cores guessing in parallel.

        double secondsPerGuess = singleGuess / numAttackers;
        //double entropy = 31.542622;
        double entropy = rt.getEntropy()
        System.out.println(Math.pow(2, 23.153260082835075d))
        System.out.println(Math.pow(2, 31.542622d))

//        NSLog(@"entropy--------==%f",entropy);
//        NSLog(@"%f",.5 * pow(2, entropy) * secondsPerGuess);
        def f = (0.5 * Math.pow(2, entropy) * secondsPerGuess);
        def r = getScore(f);

        //f > 156403.10412328536
        r == 3

    }

    def getScore(seconds){
        if (seconds < Math.pow(10, 2)) {
            return 0;
        }
        if (seconds < Math.pow(10, 4)) {
            return 1;
        }
        if (seconds < Math.pow(10, 6)) {
            return 2;
        }
        if (seconds < Math.pow(10, 8)) {
            return 3;
        }
        return 4
    }


}
