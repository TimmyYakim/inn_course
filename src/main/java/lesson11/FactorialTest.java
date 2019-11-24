package lesson11;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Задание: Перевести одну из предыдущих работ на использование стримов и лямбда-выражений там,
 * где это уместно (возможно, жертвуя производительностью)
 *
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива.
 * Использовать пул потоков для решения задачи.
 * Особенности выполнения:
 * Для данного примера использовать рекурсию - не очень хороший вариант, т.к. происходит большое выделение памяти,
 * очень вероятен StackOverFlow. Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
 * По сути, есть несколько способа решения задания:
 * 1) распараллеливать вычисление факториала для одного числа
 * 2) распараллеливать вычисления для разных чисел
 * 3) комбинированный
 * При чем вычислив факториал для одного числа, можно запомнить эти данные и использовать их для вычисления другого,
 * что будет гораздо быстрее
 *
 * @author TVYakimov
 */

public class FactorialTest {

    public static ConcurrentSkipListMap<Integer, BigInteger> processedNumbers = new ConcurrentSkipListMap<>();

    public static void main(String[] args) {
        final int upperBound = 50;
        final int lowerBound = 1;
        final int count = 30;
        int[] randomNumbers = new Random().ints(count, lowerBound, upperBound).toArray();

        try {
            final int threads = 2; // количетво потоков
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            Arrays.stream(randomNumbers)
                    .forEach(i -> {
                        Factorial factorial = new Factorial(i);
                        Future<BigInteger> result = executor.submit(factorial);
                        try {
                            processedNumbers.put(i, result.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
            executor.shutdown();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Числа и их факториалы: " + processedNumbers);
    }

}
