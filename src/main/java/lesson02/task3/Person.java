package lesson02.task3;
import java.util.*;


/**
 * Модель человека
 */
class Person implements Comparable {
    // пол
    private String sex;
    final String male = "MALE";
    final String female = "FEMALE";
    // возраст
    private int age;
    // имя
    private String name;

    public Person() {}

    public String getSex() {
        return sex;
    }

    public void setSex(boolean isMale) throws Exception {
        this.sex = isMale ? male : female;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception {
        if (age < 0 || age > 100)
            throw new Exception("Age can't be greater than 100 or negative");
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.isEmpty())
            throw new Exception("Name can't be null or empty");
        for (char ch : name.toCharArray()) {
            if (!Character.isLetter(ch))
                throw new Exception("Name should contain chars but letters");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "sex='" + sex + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * Метод, сравнивающий объекты класса Person по имени и возрасту (по условиям задачи)
     * @param o - Person
     * @return возвраает true, если поля возраст и имя совпадают
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    /**
     * Сравнивает объекты типа Person. Если возраст и имя совпадают, то выбрасывает исключение.
     * Если эти значения не совпадают, то сравнивает в порядке: пол, возраст, имя.
     * @param o другой объект типа Person
     * @return -1 или 1
     */
    @Override
    public int compareTo(Object o) {
        try {
            if (!(o instanceof Person)) {
                throw new Exception("Can't compare with not a Person: " + o.toString());
            }
            if (this.equals(o) &&
                    (this.hashCode() != o.hashCode())) // костыль. В TreeSet исключаем проверку на равенство с самим собой
                throw new Exception("Person " + this.toString() + " and " + o.toString() + " are the same" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Comparator.comparing(Person::getSex) // сравниваем по полу
                .thenComparing(Person::getAge) // по возрасту
                .reversed() // мужчины в возрасте вперед
                .thenComparing(Person::getName) // по имени
                .compare(this, (Person) o);
    }

}







