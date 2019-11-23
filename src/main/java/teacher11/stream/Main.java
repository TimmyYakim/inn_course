package teacher11.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Конвеерные методы:
 * filter, skip, distinct, map, peek, limit, sorted
 * Терминальные методы:
 * findFirst, findAny, collect, count, anyMatch, noneMatch, allMatch, min, max, forEach, forEachOrdered, toArray, reduce
 *
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {
    public static void main(String[] args) {
        Collection<String> collection = Arrays.asList("v1", "v3", "v2", "v3", "v2", "v3", "v1", "v4");
//        List<String> listAsSet = collection
//                .stream()
//                .distinct()
//                .collect(Collectors.toList());
//        System.out.println(listAsSet);

//        String join = collection
//                .stream()
//                .collect(Collectors.joining(":", " pr ", " su "));
//        System.out.println(join);

//        Map<String, String> map = collection
//                .stream()
//                .distinct()
//                .collect(Collectors.toMap(
//                        (p) -> p.substring(1, 2),
//                        (p) -> p.substring(0, 1)));
//        System.out.println(map);

//        Stream.of(1, 2, 3, 120, 410, 85, 32, 314, 12)
//                .filter(x -> x > 10)
//                .forEach(System.out::println);
//        System.out.println();

        List<String> txtList = Stream.of(1, 2, 3, 120, 410, 85, 32, 314, 12)
                .sorted()
                .skip(2)
                .limit(5)
                .map(x -> x + " ")
                .peek(System.out::print)
                .collect(Collectors.toList());

        System.out.println();
        Optional<Integer> result = txtList.parallelStream()
                .map(s -> Integer.valueOf(s.trim()))
                .peek(i -> System.out.println(Thread.currentThread().getName() + " " + i))
                .reduce(Integer::sum);

        result.ifPresent(var ->
                System.out.println("result = " + var));

    }
}