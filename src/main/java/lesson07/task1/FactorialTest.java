package lesson07.task1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
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
        List<Integer> randomNumbers = getRandomNumbers(lowerBound, upperBound, count);
        try {
            final int threads = 2; // количетво потоков
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            for (Integer i : randomNumbers) {
                Factorial factorial = new Factorial(i);
                Future<BigInteger> result = executor.submit(factorial);
                processedNumbers.put(i, result.get());
            }
            executor.shutdown();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Числа и их факториалы: " + processedNumbers);
    }

    /**
     * @param LB    нижняя граница, положительное целое
     * @param UP    верхняя граница, положительное целое
     * @param count количество генерируемых чисел
     * @return сгенерированные целые числа
     */
    private static List<Integer> getRandomNumbers(int LB, int UP, int count) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while (count > 0) {
            numbers.add(random.nextInt((UP - LB) + 1) + LB);
            count--;
        }
        return numbers;
    }


}
