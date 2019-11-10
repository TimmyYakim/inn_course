package lesson02.task3;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/*
Дан массив объектов Person. Класс Person характеризуется полями
age (возраст, целое число 0-100),
sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN),
name (имя - строка).
Создать два класса, методы которых будут реализовывать сортировку объектов. Предусмотреть единый интерфейс
для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
первые идут мужчины
выше в списке тот, кто более старший
имена сортируются по алфавиту
Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
Предусмотреть генерацию исходного массива (10000 элементов и более).
Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
 */

/**
 * @author TVYakimov
 */

class Main {
    public static void main(String[] args) {
//        int people_num = 1000; // количество людей
//        int name_length = 20; // длина имени
//        int age_bound = 100; // верхняя граница возраста
//
//        try {
//            List<Person> people = makePeople(people_num, name_length, age_bound);
//            Sort1 sort1 = new Sort1();
//            printResults(sort1, people);
//            Sort2 sort2 = new Sort2();
//            printResults(sort2, people);
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//        }
    }

    /**
     * Сортирует объекты типа Person согласно заданного порядка
     * @param sort тип сортировки
     * @param peopleList что сортируем
     */
    private static void printResults(Sort sort, List<Person> peopleList) {
        long startTime = System.nanoTime();
        Collection people = sort.sort(peopleList);
        System.out.println("Время сортировки " + (System.nanoTime() - startTime));
        System.out.println(people.toString());
    }

    /**
     * Генерирует случайный набор объектов типа Person
     * @param n количество людей
     * @param name_length длина имени (при малой длине могут возникать коллизии)
     * @param age_bound верхняя граница возраста
     * @return сгенерированные объекты Person
     * @throws Exception
     */
//    private static List<Person> makePeople(int n, int name_length, int age_bound) throws Exception {
//        List<Person> people = new ArrayList<>();
//        String alphabet = "abcdefghijklmnopqrstuvwxyz";
//        for (int i=0; i<n; i++) {
//            Person person = new Person();
//            person.setSex(new Random().nextBoolean());
//            person.setAge(new Random().nextInt(age_bound));
//            person.setName(new Random()
//                    .ints(name_length, 0, alphabet.length())
//                    .mapToObj(alphabet::charAt)
//                    .map(Object::toString)
//                    .collect(Collectors.joining()));
//            people.add(person);
//        }
//        return people;
//    }


}






