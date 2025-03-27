package com.carbooking.domain.model;

public class User {

    private String id;
    private String firstName;
    private int age;

    public User(String id, String firstName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format(
                "User{id='%s', firstName='%s', age=%d}",
                id, firstName, age);
    }

}