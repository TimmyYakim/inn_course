package lesson02.task1;

/*
Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
Смоделировав ошибку «NullPointerException»
Смоделировав ошибку «ArrayIndexOutOfBoundsException»
Вызвав свой вариант ошибки через оператор throw
 */

/**
 * @author TVYakimov
 */
public class HelloWorld {

    public static void main(String[] args) {
//        String str = null; // NullPointerException
//        String str = "Hello"; // ArrayIndexOutOfBoundsException
//        Long str = 56L; // ClassCastException
        String str = "Hello world";
        try {
            print(str);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Метод, выводящий в консоль str.
     * @param object объект для печати
     */
    private static void print(Object object) throws Exception {
        try {
            final String fixedString = "Hello world";
            String str = (String) object;
            char[] chars = str.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<fixedString.length(); i++) {
                builder.append(chars[i]);
            }
            System.out.println(builder);
        } catch (ClassCastException ex) { // мой вариант
            System.out.println("Caught inside print()");
            throw new ClassCastException("Is not a String");
        } catch (NullPointerException ex) {
            System.out.println("Caught inside print()");
            throw new NullPointerException("String is null");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Caught inside print()");
            throw new ArrayIndexOutOfBoundsException("String's length is longer than " + "Hello world".length() + " chars");
        } catch (Exception ex) {
            System.out.println("Caught inside print()");
            throw new Exception("Unknown exception");
        }
    }


}
