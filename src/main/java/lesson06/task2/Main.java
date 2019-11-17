package lesson06.task2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Создать генератор текстовых файлов, работающий по следующим правилам:
 * Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее
 * предложение (1/probability).
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
 * который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 */

public class Main {
    private static int dictionaryUpperBound = 1000; // размер словаря
    private static int n = 5; // количество файлов
    private static int size = 1000; // размер 1 файла
    private static int probability = 2; // основание для будущего вычисления вероятности
    private static char[] alphabet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'o', 'p'}; // часть алфавита
    private static int lettersLowerBound = 1; // нижняя граница букв
    private static int lettersUpperBound = 15; // верхняя граница букв
    private static int wordsLowerBound = 1; // нижняя граница слов в предложении
    private static int wordsUpperBound = 15; // верхняя граница слов в предложении
    private static int sentenceLowerBound = 1; // нижняя граница предложений в абзаце
    private static int sentenceUpperBound = 20; // верхняя граница предложений в абзаце
    private static String[] terminalSymbols = new String[]{". ", "? ", "! "};

    private static StringBuilder builder;
    private static Random random;
    private static int rest;
    private static int wordsInSentence; // счетчик слов в предложении
    private static int sentencesInParagraph; // счетчик предложений в абзаце
    private static List<String> dictionary; // основной словарь, на котором генерируется текст
    private static List<Integer> repeatings; // индексы предложений, в которых встречаются слова с probability
    private static boolean isStopped = false; // флаг приостановки генерации текста



    public static void main(String[] args) {
        List<String> words = getDictionary();
        System.out.println();
        getFiles(n, size, words, probability);
        System.out.println();
    }

    /**
     * Формирует общий словарь иразбивает его на 2 части:
     * - (words) словарь, из которого будут встречаться слова с заданной вероятностью
     * - (dictionary) основной словарь, на основе которого будет формироваться текст
     * @return words
     */
    private static List<String> getDictionary() {
        // обеспечить уникальность всех слов
        random = new Random();
        dictionary = new ArrayList<>();
        for (int j = 0; j < 2000; j++) {
            char[] newWord = new char[getLettersInWordCount()];
            for (int i = 0; i < newWord.length; i++) {
                newWord[i] = alphabet[random.nextInt(alphabet.length)];
            }
            dictionary.add(new String(newWord));
        }
        List<String> result = dictionary.subList(0, dictionaryUpperBound);
        dictionary = dictionary.subList(dictionaryUpperBound, dictionary.size());
        return result;
    }

    /**
     * Формирует случайные тексты и пишет их в файлы
     * @param n количество файлов
     * @param size размер одного файла
     * @param words словарь
     * @param probability основание для вычисления вероятности (1/probability)
     */
    private static void getFiles(/*String path,*/ int n, int size, List<String> words, int probability) {
        final String path = "C:\\...\\";
        random = new Random();
        for (int i = 0; i < n; i++) {
            builder = new StringBuilder();
            rest = size;
            boolean isTextStart = true;
            while (!isStopped) {
                appendWord(words, probability, isTextStart);
                isTextStart = false;
            }
            bufferWriteMethod(path, "Text" + i + ".txt");
            System.out.println(builder.toString().length());
            // Сбрасываем флаги
            wordsInSentence = 0;
            sentencesInParagraph = 0;
            isStopped = false;
        }
    }

    private static void appendWord(List<String> words, int probability, boolean isTextStart) {
        String newWord = getNewWord(words);
        boolean endOfSentence = false; // флаг для индикации конца предложения
        boolean endOfParagraph = false; // флаг для индикации конца абзаца
        // Если конец предложения...
        if (wordsInSentence == 0) {
            // ... но не конец абзаца, то декриментируем счетчик предложений в абзаце
            if (sentencesInParagraph > 0)
                sentencesInParagraph--;
            // ... определяем количество слов в следующем предложении
            wordsInSentence = getWordsInSentenceCount();
            // ... формируем новое слово, приводим первую букву к верхнему регистру
            newWord = upperCaseFirstLetter(newWord);
            // обозночаем конец предложения
            endOfSentence = true;
        }
        // Если конец абзаца
        if (sentencesInParagraph == 0) {
            // ... инициализируем счетчик предложений в абзаце
            sentencesInParagraph = getSentencesInParagraphCount();
            // ... определяем индексы предложений, в которых должны встречаться слова из словаря сзаданной вероятностью
            int wordsFromWordsNum = (int) ((1. / probability) * sentencesInParagraph);
            repeatings = new ArrayList<>();
            while (wordsFromWordsNum != 0) {
                repeatings.add(getSentencesInParagraphCount());
                wordsFromWordsNum--;
            }
            // обозночаем конец абзаца
            endOfParagraph = true;
        }
        // Если длина слова превышет количество симоволов для записи, то...
        if (newWord.length() + 1 > rest) {
            isStopped = true;
            // ...добьем пробелами до размера файла
            for (int i = 0; i < rest; i++) {
                builder.append(" ");
            }
            return; // ... и прекратим формирование текста.
        }
        // С заданной вероятностью должны попадаться слова из словаря
        if (repeatings.contains(sentencesInParagraph)) {
            newWord = words.get(sentencesInParagraph * wordsInSentence); // установил свое условие, тк нет требований
            if (endOfSentence)
                newWord = upperCaseFirstLetter(newWord);

        }
        // Если конец предложения, то добавляем терминальные символы,
        if (endOfSentence && !isTextStart) {
            builder.append(terminalSymbols[random.nextInt(terminalSymbols.length)]);
            rest--;
        }
        // а если конец абзаца, то перевод коретки
        if (endOfParagraph && !isTextStart) {
            builder.append("\n");
            rest--;
        }
        // Добавляем новое слово
        builder.append(newWord);
        // иногда можем добавить запятые
        wordsInSentence--;
        if (wordsInSentence != 0)
            builder.append((sentencesInParagraph + wordsInSentence) % 11 == 0 ? ", " : " ");
        // Декриментируем счетчик символов в файле ...
        rest -= (newWord.length() + 1);
        // ... и слов в предложении
    }

    /**
     *
     * @param words
     * @return
     */
    private static String getNewWord(List<String> words) {
        int words_num = random.nextInt((wordsUpperBound - wordsLowerBound) + 1) + wordsLowerBound;
        return words.get(words_num);
    }

    /**
     * Формирует случайное количество слов в предложении
     * @return количество слов
     */
    private static int getWordsInSentenceCount() {
        return random.nextInt((wordsUpperBound - wordsLowerBound) + 1) + wordsLowerBound;
    }

    /**
     * Формирует случайное количество предложений в абзаце
     * @return количество предложений
     */
    private static int getSentencesInParagraphCount() {
        return random.nextInt((sentenceUpperBound - sentenceLowerBound) + 1) + sentenceLowerBound;
    }

    /**
     * Формирует случайние количество букв в слове
     * @return количество букв
     */
    private static int getLettersInWordCount() {
        return random.nextInt((lettersUpperBound - sentenceLowerBound) + 1) + sentenceLowerBound;
    }

    /**
     * Приводит первый символ слова к верхнему регистру
     * @param word слово
     * @return обновленное слово
     */
    private static String upperCaseFirstLetter(String word) {
        if (word != null && word.length() > 0) {
            return word.substring(0, 1).toUpperCase() + word.substring(1);
        }
        return word;
    }

    /**
     * Записывает текст в файл
     * @param outputFilePath путь
     * @param filename название файла
     */
    public static void bufferWriteMethod(String outputFilePath, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outputFilePath + filename), StandardCharsets.UTF_8))) {
            writer.write(builder.toString());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}