package lesson09;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Дан интерфейс Worker
 * Необходимо написать программу, выполняющую следующее:
 * Программа с консоли построчно считывает код метода doWork. Код не должен требовать импорта дополнительных классов.
 * После ввода пустой строки считывание прекращается и считанные строки добавляются в тело метода public void doWork()
 * в файле SomeClass.java.
 * Файл SomeClass.java компилируется программой (в рантайме) в файл SomeClass.class.
 * Полученный файл подгружается в программу с помощью кастомного загрузчика
 * Метод, введенный с консоли, исполняется в рантайме (вызывается у экземпляра объекта подгруженного класса)
 */

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException {
        // Полный путь скрыл, ... заменить на свой вариант
        final String path = "...\\inn_course\\src\\main\\java\\lesson09\\";

        // Считываем файл
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(Paths.get(path + "SomeClass.java")).forEach(builder::append);
        String source = builder.toString();
//        Вводим строки для вставки в конслоль
//        int a = 1;
//        int b = 2;
//        int i = a + b;
//        System.out.println(i);
        String[] parts = source.split("doWork\\(" + "\\)" + "\\{" + "\\}");
        String message;
        // Формируем тело метода
        builder = new StringBuilder();
        builder.append(parts[0]);
        builder.append("doWork(){");
        while (!(message = scanner.nextLine()).isEmpty()) {
            builder.append(message);
        }
        builder.append("}");
        builder.append(parts[1]);

        File sourceFile = new File(path, "SomeClass.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), builder.toString().getBytes());

        // Компилируем сохраненный файл
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Загружаем класс, вызываем метод
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(path).toURI().toURL()});
        Class<?> cls = Class.forName("lesson09.SomeClass", true, classLoader);
        Object instance = cls.newInstance();
        if (instance instanceof Worker)
            ((Worker) instance).doWork();
        System.out.println(instance);
    }

}
