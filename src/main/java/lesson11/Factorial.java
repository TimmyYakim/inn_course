package lesson11;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

/**
 * Factorial
 *
 * @author TVYakimov
 */

public class Factorial implements Callable<BigInteger> {

    private final int n;

    public Factorial(int n) {
        this.n = n;
    }

    /**
     * Вычисляет факториал числа n
     * @return факториал числа n
     */
    @Override
    public BigInteger call() {

        BigInteger result = FactorialTest.processedNumbers
                .entrySet()
                .stream()
                .filter(e -> new Integer(n).equals(e.getKey()))
                .map(Map.Entry::getValue)
                .findFirst().orElse(null);
        // ранее вычисляли, то возвращаем результат
        if (result != null)
            return result;

        // Получаем ближайший снизу элемент, для которого ранее  делали вычисления
        Integer closestSmall;
        // если такой элемент есть, то расчеты будем производить, начиная с уже ранее вычисленного его факториала
        if ((closestSmall = FactorialTest.processedNumbers.floorKey(n)) != null) {
            result = FactorialTest.processedNumbers.get(closestSmall);
        } else {
            result = BigInteger.valueOf(1);
            closestSmall = 0;
        }
        // расчет факториала
        return IntStream
                .rangeClosed(closestSmall + 1, n)
                .mapToObj(BigInteger::valueOf)
                .reduce(result, BigInteger::multiply);
    }




}