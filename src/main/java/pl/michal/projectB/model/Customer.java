package pl.michal.projectB.model;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String surname;
    private Integer age;

    public Customer() {
        this.id = UUID.randomUUID();
    }

    public Customer(String[] data) {
        this.id = UUID.randomUUID();
        this.name = data[0];
        this.surname = data[1];
        this.age = checkAge(data[2]);
    }

    private Integer checkAge(String age) {
        try {
            return Integer.parseInt(age);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(String age) {
        this.age = checkAge(age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
