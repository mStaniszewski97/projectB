package pl.michal.projectB.model;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String surname;
    private int age;

    public Customer(String[] data) {
        this.id = UUID.randomUUID();
        this.name = data[0];
        this.surname = data[1];
        this.age = Integer.parseInt(data[2]);
    }

    public UUID getId() {
        return id;
    }
}
