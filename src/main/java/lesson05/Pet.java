package lesson05;

import lesson02.task3.Person;
import java.util.Objects;

/**
 * Класс Питомец
 * Класс картотеки питомцев.
 */
class Pet {

    // идентификатор
    private Integer petId;
    // кличка
    private String petName;
    // вес
    private Float petWeight;
    // хозяин
    private Person master;

    Pet(String petName, Float petWeight, Person master) {
        setPetName(petName);
        setPetWeight(petWeight);
        setMaster(master);
    }

    public Integer getPetId() {
        return petId;
    }

    void setPetId(Integer petId) {
        try {
            if (petId < 0) {
                throw new IllegalArgumentException("Id can't be negative");
            }
            this.petId = petId;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.toString());
        }
    }

    String getPetName() {
        return petName;
    }

    void setPetName(String petName) {
        this.petName = petName;
    }

    Float getPetWeight() {
        return petWeight;
    }

    void setPetWeight(Float petWeight) {
        try {
            if (petWeight < 0f) {
                throw new IllegalArgumentException("Weight can't be negative");
            }
            this.petWeight = petWeight;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.toString());
        }
    }

    Person getMaster() {
        return master;
    }

    void setMaster(Person master) {
        try {
            if (master == null) {
                throw new IllegalArgumentException("Master is not assigned");
            }
            this.master = master;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(petId, pet.petId) &&
                Objects.equals(petName, pet.petName) &&
                Objects.equals(petWeight, pet.petWeight) &&
                Objects.equals(master, pet.master);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, petName, petWeight, master);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", petWeight=" + petWeight +
                ", master=" + master +
                '}';
    }
}
