package lesson02.task3;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
    Класс сортировки, использующий Collections.sort(people)
 */

class Sort1 implements Sort {

    public Collection<Person> sort(List<Person> people) {
        Collections.sort(people);
        return people;
    }
}
