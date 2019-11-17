package lesson06.task1;

import java.util.Set;

/**
 * Написать программу, читающую текстовый файл. Программа должна составлять отсортированный по алфавиту список слов,
 * найденных в файле и сохранять его в файл-результат. Найденные слова не должны повторяться,
 * регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 * @author TVYakimov
 */
public class Main {

    final static private String textFilePath = "C:\\...\\text.txt";
    final static private String dictionaryPath = "C:\\...\\dictionary.txt";

    public static void main(String[] args) {
        // получаем словарь на основе текста
        Set<String> words = DictionaryUtil.bufferReadMethod(textFilePath);
        // пишем словарь в файл
        DictionaryUtil.bufferWriteMethod(words, dictionaryPath);
    }






}
