package teacher11.thread;

/**
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Message from " + Thread.currentThread().getName());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Message from " + Thread.currentThread().getName());
            }
        }).start();
    }
}