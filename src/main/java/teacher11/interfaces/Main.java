package teacher11.interfaces;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Main.
 *
 * @author Ilya_Sukhachev
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {

    public static void main(String[] args) {
//        Consumer<String> greedy = (input) -> {
//            System.out.println("input: " + input);
//        };
//        greedy.accept("Hello");

//        Predicate<Integer> predicateMoreThan = (num) -> {
//          return num > 10;
//        };
//        System.out.println(predicateMoreThan.negate().test(9));
//
//        Predicate<Integer> predicateLessThanZero = (num) -> {
//            return num < 0;
//        };
//
//        Predicate<Integer> predicate = (num) -> {
//            return num % 2 == 0;
//        };
//
//        Boolean result = predicateLessThanZero
//                .or(predicateMoreThan.and(predicate)).test(16);
//
//        System.out.println(result);

//        Function<Integer, String> integerToString = (arg) -> arg.toString();
//
//        Function<String, Integer> strToInt = (arg) -> Integer.parseInt(arg);
//
//        System.out.println(integerToString
//                .compose(strToInt.compose(integerToString)).apply(78));

//        Supplier<Something> somethingSupplier = Something::new;
//        somethingSupplier.get();
//
//        Supplier<Something> somethingSupplierFact = SomethingFactoty::createSome;
//        somethingSupplierFact.get();
//
//        Supplier<Something> somethingSupplierCustom = () -> new Something();
//        somethingSupplierCustom.get();

        Comparator<Integer> comparator = ((o1, o2) -> o1-o2);
        System.out.println(comparator.compare(1, 2));

        Optional<String> optionalFull = Optional.of("some");
        Optional<String> optionalEmpty = Optional.ofNullable(null);
//        String msg = "empty";
//        Optional<String> optionalEmpty = Optional.of(msg);
//        msg = null;
        System.out.println(optionalFull.isPresent());
        System.out.println(optionalEmpty.isPresent());
    }
}