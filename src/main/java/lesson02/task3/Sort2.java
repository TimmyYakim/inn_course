package lesson02.task3;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;


/*
    Класс сортировки, использующий TreeSet
 */
class Sort2 implements Sort {

    public Collection<Person> sort(List<Person> people) {
        return new TreeSet<>(people);
    }

}