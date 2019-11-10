package lesson05;

import lesson02.task3.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс картотеки питомцев.
 * @author TVYakimov
 */
class PetCards {

    // Основание для сортировки
    public enum SortBy {
        PET_NAME, PET_MASTER, PET_WEIGHT
    }

    // счетчик картотеки
    private int topId;

    // список питомцев
    private List<Pet> petIds;

    // еще одна структура с питомцами
    private HashMap<String, Pet> pets;

    /**
     * Конструктор картотеки
     */
    PetCards() {
        this.topId = 0;
        this.petIds = new ArrayList<>();
        this.pets = new HashMap<>();
    }

    /**
     * Добавление сведений о питомце в картотеку
     * @param pet питомец. Если сведения о питомце уже содержатся в картотеке, то выбросит исключение
     *
     */
    void addPet(Pet pet) {
        try {
            if (pets.containsValue(pet)) {
                throw new Exception("That pet exists");
            }
            pet.setPetId(topId);
            petIds.add(topId, pet);
            pets.put(pet.getPetName(), pet);
            topId++;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Находит питомцев по имени
     * @param petName имя питомца
     * @return множество питомцев или null если их нет
     */
    Set<Pet> getPet(String petName) {
        Set<Pet> foundPets = new HashSet<>();
        for (Pet pet: pets.values()) {
            if (pet.getPetName().equals(petName)) {
                foundPets.add(pet);
            }
        }
        return foundPets;
    }

    /**
     * Устанавливает новое значение имени
     * @param id - идентификатор питомца
     * @param petName - новая кличка
     */
    void setNewPetName(Integer id, String petName) {
        getPetById(id).setPetName(petName);
    }

    /**
     * Устанавливает новое значение хозяина
     * @param id - идентификатор питомца
     * @param m - новый хозяин
     */
    void setNewPetMaster(Integer id, Person m) {
        getPetById(id).setMaster(m);
    }

    /**
     * Устанавливает новое значение веса
     * @param id - идентификатор питомца
     * @param w - новый вес питомца
     */
    void setNewPetWeight(Integer id, Float w) {
        getPetById(id).setPetWeight(w);
    }

    /**
     * Находит питомца по его идентификатору
     * @param id - идентификатор питомца
     * @return питомца
     */
    private Pet getPetById(Integer id) {
        try {
            if (id < 0) {
                throw new Exception("Id can't be negative");
            }
            if (petIds.get(id) == null) {
                throw new Exception("There is no pet for that id");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return petIds.get(id);
    }

    /**
     * Выводит на экран питомцев отсортированных по заданному основанию
     * @param base - основание сортировки
     */
    void printPetsBy(SortBy base) {
        switch (base) {
            case PET_NAME:
                System.out.println("По кличке: " +
                        pets
                        .values()
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
                        .values()
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
                        .values()
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
        return "PetCards{" +
                "pets=" + pets +
                '}';
    }
}
