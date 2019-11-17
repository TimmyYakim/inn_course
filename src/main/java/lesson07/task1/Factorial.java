package lesson07.task1;

import java.math.BigInteger;
import java.util.concurrent.Callable;

/**
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
        BigInteger result = BigInteger.valueOf(1);
        // ранее вычисляли, то возвращаем результат
        if (FactorialTest.processedNumbers.containsKey(n)) {
            return FactorialTest.processedNumbers.get(n);
        }
        // Получаем ближайший снизу элемент, для которого ранее  делали вычисления
        Integer closestSmall = FactorialTest.processedNumbers.floorKey(n);
        // если такой элемент есть, то расчеты будем производить, начиная с уже ранее вычисленного его факториала
        if (closestSmall != null) {
            result = FactorialTest.processedNumbers.get(closestSmall);
        } else {
            closestSmall = 0;
        }
        // расчет факториала
        for (int i = (closestSmall + 1); i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}