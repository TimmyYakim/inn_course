package lesson12.task1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться, но и периодически частично удаляться,
 * чтобы GC имел возможность очищать часть памяти. Через некоторое время программа должна завершиться
 * с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 *
 * @author Timofey Yakimov
 */
public class MemoryLeakageTest {

    //-XX:+UseSerialGC -Xmx1024m
    // Java 8
    public static void main(String[] args) throws InterruptedException {
        List<Double> digits = new ArrayList<>();
        Thread.sleep(20000);
        while (true) {
            digits.add(Math.random());
        }
    }

}
