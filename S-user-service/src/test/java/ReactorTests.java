import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class ReactorTests {
    List<String> list;
    long l;

    @Before
    public void setUp() throws Exception {

        l = 100000000;
        list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            list.add(String.valueOf(i));
        }
    }


    @Test
    public void streamTest() {

        long s1 = System.currentTimeMillis();
        List<Integer> elements = list.parallelStream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
        long s2 = System.currentTimeMillis();
        System.out.println("stream:" + (s2 - s1) + ":" + elements.size());
    }


    @Test
    public void fluxTest() {

        long s1 = System.currentTimeMillis();
        List<Integer> elements = new ArrayList<>();

        Flux.fromIterable(list).map(s -> Integer.valueOf(s))
                .subscribe(elements::add);
        long s2 = System.currentTimeMillis();
        System.out.println("flux:" + (s2 - s1) + ":" + elements.size());
    }


    public static void main(String agrs[]) {
        long l = 30000000;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println("start...");

        long s1 = System.currentTimeMillis();
        List<Integer> elements = new ArrayList<>();
        Flux.fromIterable(list).map(s -> Integer.valueOf(s))
                .subscribe(elements::add);
        long s2 = System.currentTimeMillis();
        System.out.println("flux:" + (s2 - s1) + ":" + elements.size());



        long s3 = System.currentTimeMillis();
        List<Integer> elements1 = list.parallelStream().map(s -> Integer.valueOf(s)).collect(Collectors.toList());
        long s4 = System.currentTimeMillis();
        System.out.println("stream:" + (s4 - s3) + ":" + elements1.size());











        System.out.println("end");

    }
}