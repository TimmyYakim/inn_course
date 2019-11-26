package teacher12;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Для использования Flight Recorder в JMC добавьте флаги:
 * -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 * Пример 1: -XX:+UseSerialGC -Xmx100m
 * Пример 2: -XX:+UseParallelGC -Xmx100m
 * Пример 3: -XX:+UseConcMarkSweepGC -Xmx100m
 * Пример 3: -XX:+UseG1GC -Xmx100m
 *
 * Source: github.com/lifeandfree/gc
 */
public class GCDemo {
    private static final int LOOP_COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < LOOP_COUNT; i++) {
            String str = "" + random.nextInt();
            list.add(str);
            if (i % 10 == 0) {
                Thread.sleep(1);
                list.remove(0);
            }
        }
        System.out.println(list.size());

    }

    static void demoOutOfMemoryError() throws InterruptedException {
        Thread.sleep(10_000);
        int multiplier = 100;
        for (int i = 1; i < 50; i++) {
            Thread.sleep(1);
            System.out.println("Round " + i + " Free Memory: " + Runtime.getRuntime().freeMemory());
            int[] myIntList = new int[multiplier];
            for (int j = i; j > 1; j--) {
                myIntList[j] = i;
            }
            multiplier = multiplier * 10;
        }
    }

    static void demoGCOverhead() {
        Map<Object, Object> map = System.getProperties();
        Random r = new Random();
        while (true) {
            map.put(r.nextInt(), "GC Overhead Limit Test");
        }
    }
}