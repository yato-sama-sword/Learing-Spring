package edu.neu.spring.beans;


import java.util.Objects;

public class User {
    private int age;
    private String name;
    private Pet pet;

    public User() {
    }

    public User(int age, String name, Pet pet) {
        this.age = age;
        this.name = name;
        this.pet = pet;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", pet=" + pet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(name, user.name) && Objects.equals(pet, user.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, pet);
    }
}
