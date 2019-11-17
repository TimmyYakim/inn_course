package lesson05;

import lesson02.task3.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс картотеки питомцев.
 * @author TVYakimov
 */
public class PetCardsManager {

    // Основание для сортировки
    public enum SortBy {
        PET_NAME, // имя
        PET_MASTER, // хозяин
        PET_WEIGHT // вес
    }

    CompositeListMap<String, Pet> pets;

    public PetCardsManager() {
        pets = new CompositeListMap<>();
    }


    /**
     * Добавление сведений о питомце в картотеку
     *
     * @param pet питомец. Если сведения о питомце уже содержатся в картотеке, то выбросит исключение
     */
    public void addPet(Pet pet) {
        try {
            int petId = pets.add(pet.getPetName(), pet);
            pet.setPetId(petId);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Находит питомцев по имени
     *
     * @param petName имя питомца
     * @return множество питомцев или null если их нет
     */
    public Set<Pet> getPet(String petName) {
        return pets.get(petName);
    }

    /**
     * Устанавливает новое значение имени
     *
     * @param id      - идентификатор питомца
     * @param petName - новая кличка
     */
    public void setNewPetName(Integer id, String petName) {
        getPetById(id).setPetName(petName);
    }

    /**
     * Устанавливает новое значение хозяина
     *
     * @param id - идентификатор питомца
     * @param m  - новый хозяин
     */
    public void setNewPetMaster(Integer id, Person m) {
        getPetById(id).setMaster(m);
    }

    /**
     * Устанавливает новое значение веса
     *
     * @param id - идентификатор питомца
     * @param w  - новый вес питомца
     */
    public void setNewPetWeight(Integer id, Float w) {
        getPetById(id).setPetWeight(w);
    }

    /**
     * Находит питомца по его идентификатору
     *
     * @param id - идентификатор питомца
     * @return питомца
     */
    private Pet getPetById(Integer id) {
        Pet result = null;
        try {
            result = (Pet) pets.get(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    /**
     * Выводит на экран питомцев отсортированных по заданному основанию
     *
     * @param base - основание сортировки
     */
    public void printPetsBy(SortBy base) {
        switch (base) {
            case PET_NAME:
                System.out.println("По кличке: " +
                        pets
                                .getList()
                                .stream()
                                .sorted(new Comparator<Pet>() {
                                    @Override
                                    public int compare(Pet o1, Pet o2) {
                                        return o1.getPetName().compareTo(o2.getPetName());
                                    }
                                })
                                .collect(Collectors.toList()));
                break;
            case PET_MASTER:
                System.out.println("По хозяину: " +
                        pets
                                .getList()
                                .stream()
                                .sorted(new Comparator<Pet>() {
                                    @Override
                                    public int compare(Pet o1, Pet o2) {
                                        return o1.getMaster().compareTo(o2.getMaster());
                                    }
                                })
                                .collect(Collectors.toList()));
                break;
            case PET_WEIGHT:
                System.out.println("По весу: " +
                        pets
                                .getList()
                                .stream()
                                .sorted(new Comparator<Pet>() {
                                    @Override
                                    public int compare(Pet o1, Pet o2) {
                                        return o1.getPetWeight().compareTo(o2.getPetWeight());
                                    }
                                })
                                .collect(Collectors.toList()));
                break;
            default:
                System.out.println("No such sort base");
                break;
        }
    }

    @Override
    public String toString() {
        return "PetCardsManager{" +
                "pets=" + pets +
                '}';
    }
}
