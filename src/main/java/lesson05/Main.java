package lesson05;

import lesson02.task3.Person;

/*
Разработать программу – картотеку домашних животных. У каждого животного есть уникальный идентификационный номер,
кличка, хозяин (объект класс Person с полями – имя, возраст, пол), вес.

Реализовать:
метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
поиск животного по его кличке (поиск должен быть эффективным)
изменение данных животного по его идентификатору
вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */

/**
 * @author TVYakimov
 */
public class Main {

    public static void main(String[] args) {
        PetCards petCards = new PetCards(); // создаем картотеку
        // Создаем хозяев. Класс Person возьмем из предыдущего ДЗ
        Person mater1 = new Person(true, "Sasha", 20);
        Person mater2 = new Person(true, "Misha", 23);
        Person mater3 = new Person(false, "Anya", 19);
        Person mater4 = new Person(false, "Tanya", 26);
        // Назначаем им питомцев
        Pet pet1 = new Pet("Kesha", 22f, mater1);
        Pet pet2 = new Pet("Gosha", 19f, mater2);
        Pet pet3 = new Pet("Rex", 1f, mater3);
        Pet pet4 = new Pet("Mursic", 60f, mater4);
        Pet pet5 = new Pet("Kesha", 22f, mater4);
        // Учитываем питомцев в картотеке
        petCards.addPet(pet1);
        petCards.addPet(pet2);
        petCards.addPet(pet3);
        petCards.addPet(pet4);
        petCards.addPet(pet5);
        //Отсортируем
        petCards.printPetsBy(PetCards.SortBy.PET_NAME);
        petCards.printPetsBy(PetCards.SortBy.PET_MASTER);
        petCards.printPetsBy(PetCards.SortBy.PET_WEIGHT);
        // Найдем
        System.out.println(petCards.getPet("Kesha"));
        // Поменяем хозяина
        petCards.setNewPetMaster(1, new Person(false, "Ksenia", 30));
        // кличку
        petCards.setNewPetName(1, "Rex");
        // вес
        petCards.setNewPetWeight(1, 5f);
        // Снова напечатаем
        petCards.printPetsBy(PetCards.SortBy.PET_NAME);
    }



}
