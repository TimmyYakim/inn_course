package lesson06.task1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


/**
 *  @author TVYakimov
 */
public class DictionaryUtil {

    /**
     * Получает множество уникальных слов из текстового файла, не учитывая словоформы
     * @param inputFilePath текстовый файл
     * @return множество уникальных слов
     */
    public static Set<String> bufferReadMethod(String inputFilePath) {
        Set<String> words = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(inputFilePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line.toLowerCase(), " \t\n\r\f,.:;?![]'");
                while (tokenizer.hasMoreTokens()) {
                    words.add(tokenizer.nextToken());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return words;
    }

    /**
     * Записывает слова в файл
     * @param words множество записываемых слова
     * @param outputFilePath файл для записи
     */
    public static void bufferWriteMethod(Set<String> words, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outputFilePath), StandardCharsets.UTF_8))) {
            for (String word : words) {
                writer.write(word + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
