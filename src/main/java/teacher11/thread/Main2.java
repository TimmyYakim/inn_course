package teacher11.thread;

/**
 * Source: github.com/lifeandfree/stream-api
 */
public class Main2 {
    public static void main(String[] args) {
        System.out.println("Message from " + Thread.currentThread().getName());
        new Thread(() -> System.out.println("Message from " + Thread.currentThread().getName())).start();
    }
}