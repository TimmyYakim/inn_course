package lesson03.task2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Класс ObjectBox
 * @param <Object>
 * @author TVYakimov
 */

public class ObjectBox<Object> {

    private Collection<Object> collection;

    public ObjectBox() {
        this.collection = new HashSet<>();
    }

    /**
     * Добавление объекта в коллекцию
     * @param o объект
     */
    public void addObject(Object o) {
        this.collection.add(o);
    }

    /**
     * Удаление объекта из коллекции
     * @param o объект
     */
    public void deleteObject(Object o) {
        this.collection.remove(o);
    }

    /**
     * Вывод коллекции на экран
     */
    public void dump() {
        System.out.println(collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }

    @Override
    public String toString() {
        return "ObjectBox{" +
                "collection=" + collection +
                '}';
    }
}
