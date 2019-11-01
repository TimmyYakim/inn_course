package lesson02.task3;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс для классов сортировки
 */

interface Sort {

    Collection<Person> sort(List<Person> people);

}