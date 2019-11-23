package teacher11.method_reference;

/**
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {
    public static void main(String[] args) {
        Greeter sayHi = new GreeterImpl()::sayHiBrightly;
        sayHi.sayHi();

        sayHi = GreeterImpl::sayHi;
        sayHi.sayHi();

        Greeter greeter = () -> System.out.println("hi");
        greeter.sayHi();
    }
}