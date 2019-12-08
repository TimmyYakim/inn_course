package lesson12.task2;

//import javassist.ClassPool;
//import javassist.CannotCompileException;

/**
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation
 *
 * @author Timofey Yakimov
 */
public class MetaspaceTest {

    public static void main(String[] args) {
        testPerm();
//        testMetaspace();
    }

    // -Xmx8192m -XX:MaxPermSize=4096m
    // JDK7
    private static void testPerm() {
        StringBuilder s = new StringBuilder();
        while (true) {
            s.append("V");
        }
    }

    // -XX:MaxMetaspaceSize=64m
    // JDK8
    // ClassPool из javassist
//    private static void testMetaspace() throws CannotCompileException {
//        ClassPool cp = ClassPool.getDefault();
//        int i=0;
//        while (true) {
//            Class c = cp.makeClass("lesson12.test2.Metaspace" + (i++)).toClass();
//        }
//    }

}