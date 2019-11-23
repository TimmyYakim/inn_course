package teacher11.lambds_cycle;

import java.util.Arrays;
import java.util.List;

/**
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

//        for (int number : numbers) {
//            System.out.println(number);
//        }

//        numbers.forEach(value -> System.out.println(value));

        numbers.parallelStream().map(integer -> integer).filter(integer -> integer > 2)
                .forEach(value -> System.out.println(value));

//        MyNum myNum = n -> (n % 2) == 0;
//
//        System.out.println(myNum.test(17));
    }

    interface MyNum {
        Boolean test(Integer n);
    }
}