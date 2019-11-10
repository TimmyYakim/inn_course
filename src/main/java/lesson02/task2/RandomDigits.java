package lesson02.task2;

/*
Составить программу, генерирующую N случайных чисел. Для каждого числа k вычислить квадратный корень q.
Если квадрат целой части q числа равен k, то вывести это число на экран.
Предусмотреть что первоначальные числа могут быть отрицательные, в этом случае генерировать исключение.
 */


import java.util.Random;

/**
 * @author TVYakimov
 */
public class RandomDigits {

    public static void main(String[] args) {
        final int min = -2;
        final int max = 500;
        final int n = 100;
        try {
            printDigits(min, max, n);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Метод выводит на печать число типа int если целая часть от его квадратного корня, возведенная в квадрат,
     * будет равна этому числу
     * @param min от (диапазон)
     * @param max до (диапазон)
     * @param n сколько чисел генерировать
     * @throws IllegalArgumentException Если встречается отрицательное число
     */
    private static void printDigits(int min, int max, int n) throws IllegalArgumentException {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int k = random.nextInt(max - min) + min;
            if (k < 0)
                throw new IllegalArgumentException(k + " is negative");
            int q = (int)Math.sqrt(k);
            if (q*q == k)
                System.out.println(k);
        }
    }

}
