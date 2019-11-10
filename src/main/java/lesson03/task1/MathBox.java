package lesson03.task1;

import lesson03.task2.ObjectBox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс MathBox
 * Хранит объекты класса Number и его потомков
 * @param <T>
 * @author TVYakimov
 */
public class MathBox<T extends Number>  extends ObjectBox {

    private Set<T> numbers;

    public MathBox(T[] numbers) {
        this.numbers = new HashSet<>();
        this.numbers.addAll(Arrays.asList(numbers));
    }

    /**
     * Возвращает сумму значений всех элементов в коллекции
     * @return сумма значений
     */
    public BigDecimal sum/*summator*/() {
        return numbers
                .parallelStream()
                .map(T::toString) // исключяем2 длинные хвосты
                .map(BigDecimal::new)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Поэлементное деление элементов на делитель
     * @param div делитель
     */
    public void divide/*splitter*/(Number div) {
        try {
            if (div.equals(0))
                throw new ArithmeticException("Div can't be zero");
            Set<Number> newNumbers = new HashSet<>();
            for (T number : numbers) {
                newNumbers.add(new BigDecimal(number.toString()).divide(new BigDecimal(div.toString())));
            }
            this.numbers = (Set<T>) newNumbers;
        } catch(ArithmeticException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Добавляет объект в коллекцию.
     * @param o объект для добавления в коллекцию
     */
    @Override
    public void addObject(Object o) {
        try {
            System.out.println(o.getClass());
            if (!(o instanceof Number)) {
                throw new ClassCastException("Object of class Object can't be added");
            }
        } catch (ClassCastException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Удаляет Integer
     * @param i удаляемый объект
     */
    public void removeInteger(Integer i) {
        numbers.remove(i);
    }

    @Override
    public void dump() {
        System.out.println(numbers);;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MathBox<?> mathBox = (MathBox<?>) o;
        return Objects.equals(numbers, mathBox.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numbers);
    }

    @Override
    public String toString() {
        return "MathBox{" +
                "numbers=" + numbers +
                '}';
    }
}
